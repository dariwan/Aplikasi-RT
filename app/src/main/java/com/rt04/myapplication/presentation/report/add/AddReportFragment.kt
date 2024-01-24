package com.rt04.myapplication.presentation.report.add

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentAddReportBinding
import com.rt04.myapplication.presentation.main.MainActivity
import java.util.UUID

class AddReportFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentAddReportBinding
    private var db = Firebase.firestore
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(
                    requireContext(),
                    "Notifications permission granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Notifications permission rejected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddReportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setupButton()
    }

    private fun setupButton() {
        binding.ivBack.setOnClickListener(this)
        binding.btnLaporkan.setOnClickListener(this)
        binding.btnUploadImage.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.iv_back -> {
                findNavController().navigate(R.id.action_addReportFragment_to_reportFragment)
            }

            R.id.btn_laporkan -> {
                buatLaporan()
            }

            R.id.btn_upload_image -> {
                openGaleri()
            }
        }
    }

    private fun buatLaporan() {
        val topik = binding.topikEditText.text.toString()
        val masalah = binding.masalahEditText.text.toString()
        val nama = binding.namaEditText.text.toString()
        val kategori = binding.spCategory.selectedItem.toString()

        uploadGambar(selectedImageUri!!) { imageUrl ->
            val reportData = hashMapOf(
                "topik" to topik,
                "masalah" to masalah,
                "nama" to nama,
                "kategori" to kategori,
                "image" to imageUrl
            )

            binding.progressBar.visibility = View.VISIBLE

            db.collection("laporan")
                .add(reportData)
                .addOnSuccessListener {
                    showNotification(topik, kategori)
                    Toast.makeText(requireContext(), "Laporan berhasil dibuat", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_addReportFragment_to_reportFragment)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        requireContext(),
                        "Gagal membuat laporan: $exception",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun showNotification(topik: String, kategori: String) {
        createNotificationChannel()

        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationContent =
            NotificationCompat.Builder(requireContext(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Laporan Baru")
                .setContentText("Topik: $topik, Kategori: $kategori")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        Log.d("Notification", "Trying to show notification.")
        Log.d("Notification", "Topik: $topik, Kategori: $kategori")

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.VIBRATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            with(NotificationManagerCompat.from(requireContext())) {
                notify(NOTIFICATION_ID, notificationContent)
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.VIBRATE),
                REQUEST_CODE_PERMISSION_VIBRATE
            )
        }
    }


    private fun uploadGambar(imageUri: Uri, callback: (String) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                callback(downloadUri.toString())
            } else {
                Log.e("Upload Gambar", "Gagal mengunggah gambar: ${task.exception}")
                Toast.makeText(requireContext(), "Gagal mengunggah gambar", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun openGaleri() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            binding.ivUploadImage.setImageURI(selectedImageUri)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "your_channel_id"
        const val NOTIFICATION_ID = 1
        const val REQUEST_CODE_PERMISSION_VIBRATE = 123
    }
}