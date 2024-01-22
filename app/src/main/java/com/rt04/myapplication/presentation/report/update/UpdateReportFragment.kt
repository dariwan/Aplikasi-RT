package com.rt04.myapplication.presentation.report.update

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Kegiatan
import com.rt04.myapplication.core.data.Report
import com.rt04.myapplication.databinding.FragmentUpdateReportBinding
import com.rt04.myapplication.presentation.information.kegiatan.update.UpdateKegiatanFragment
import java.util.UUID

class UpdateReportFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentUpdateReportBinding
    private var db = Firebase.firestore
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUpdateReportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
        getData()
    }

    private fun setupButton() {
        binding.ivBack.setOnClickListener(this)
        binding.btnLaporkan.setOnClickListener(this)
        binding.btnUploadImage.setOnClickListener(this)
    }

    private fun getData() {
        val data = arguments?.getParcelable<Report>(NAME)
        Log.d("UpdateKegiatanFragment", "Data update: $data")
        data?.let {
            binding.topikEditText.setText(it.topik)
            binding.masalahEditText.setText(it.masalah)
            binding.namaEditText.setText(it.nama)
            Glide.with(requireContext())
                .load(it.image)
                .into(binding.ivUploadImage)
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_back -> {
                findNavController().navigate(R.id.action_updateReportFragment_to_reportFragment)
            }
            R.id.btn_laporkan -> {
                updateReport()
            }
            R.id.btn_upload_image -> {
                openGaleri()
            }
        }
    }

    private fun updateReport() {
        val topik = binding.topikEditText.text.toString()
        val masalah = binding.masalahEditText.text.toString()
        val nama = binding.namaEditText.text.toString()
        val kategori = binding.spCategory.selectedItem.toString()

        uploadGambar(selectedImageUri!!){imageUrl ->
            val reportData = hashMapOf(
                "topik" to topik,
                "masalah" to masalah,
                "nama" to nama,
                "kategori" to kategori,
                "image" to imageUrl
            )

            binding.progressBar.visibility = View.VISIBLE

            val reportId = arguments?.getString(ID)

            if (reportId != null) {
                db.collection("laporan")
                    .document(reportId)
                    .update(reportData as Map<String, Any>)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Laporan berhasil diubah", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_updateReportFragment_to_reportFragment)
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(requireContext(), "Gagal ubah laporan: $exception", Toast.LENGTH_SHORT).show()
                    }
            }
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
                Toast.makeText(requireContext(), "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            selectedImageUri = data?.data
            binding.ivUploadImage.setImageURI(selectedImageUri)
        }
    }

    companion object {
        var NAME = "report"
        var ID = "reportId"
    }
}