package com.rt04.myapplication.presentation.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentProfileBinding
import com.rt04.myapplication.presentation.login.LoginActivity
import com.rt04.myapplication.presentation.login.LoginActivity.Companion.PREFS_NAME

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding
    private var db = Firebase.firestore
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setData()
    }

    private fun setData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("user").document(userId).get()
                .addOnSuccessListener { document ->
                    val username = document.getString("username")
                    val email = document.getString("email")
                    val role = document.getString("category")
                    val image = document.getString("photoUrl")

                    binding.tvUsername.text = username
                    binding.tvEmail.text = email
                    binding.tvCategory.text = role

                    Glide.with(requireContext())
                        .load(image)
                        .into(binding.ivPhotoProfile)

                    if (role == "Warga") {
                        binding.btnAdd.visibility = View.GONE
                    } else {
                        binding.btnAdd.visibility = View.VISIBLE
                        binding.btnIncome.visibility = View.VISIBLE
                        binding.btnSpending.visibility = View.VISIBLE
                    }
                }
        }
    }

    private fun setupView() {
        binding.btnAdd.setOnClickListener(this)
        binding.btnLaporan.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
        binding.ivPhotoProfile.setOnClickListener(this)
        binding.btnIncome.setOnClickListener(this)
        binding.btnSpending.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_add -> {
                findNavController().navigate(R.id.action_profileFragment_to_kegiatanKetuaFragment)
            }

            R.id.btn_laporan -> {
                findNavController().navigate(R.id.action_profileFragment_to_reportFragment)
            }

            R.id.btn_income -> {
                findNavController().navigate(R.id.action_profileFragment_to_reportIncomeKetuaFragment)
            }

            R.id.btn_spending -> {
                findNavController().navigate(R.id.action_profileFragment_to_reportSpendingKetuaFragment)
            }

            R.id.btn_logout -> {
                deleteDataSharedPref()
            }

            R.id.iv_photo_profile -> {
                openGaleri()
            }
        }
    }

    private fun deleteDataSharedPref() {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
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
            binding.ivPhotoProfile.setImageURI(selectedImageUri)

            updateProfileImage(selectedImageUri)
        }
    }

    private fun updateProfileImage(imageUri: Uri?) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null && imageUri != null) {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val imageRef = storageRef.child("image/${userId}_profile.jpg")

            imageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnaphot ->
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        updateImageUrlInFirestore(userId, uri.toString())
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Gagal upload gambar", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun updateImageUrlInFirestore(userId: String, imageUrl: String) {
        val userRef = db.collection("user").document(userId)

        userRef.update("photoUrl", imageUrl)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Gambar berhasil disimpan", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Gambar gagal disimpan", Toast.LENGTH_SHORT).show()
            }
    }

}