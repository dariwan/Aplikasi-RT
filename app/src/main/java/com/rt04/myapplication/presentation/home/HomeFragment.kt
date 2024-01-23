package com.rt04.myapplication.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Kegiatan
import com.rt04.myapplication.core.utils.SessionManager
import com.rt04.myapplication.databinding.FragmentHomeBinding
import com.rt04.myapplication.presentation.adapter.KegiatanAdapter

class HomeFragment : Fragment() {
    private lateinit var sharedPref: SessionManager
    private var username: String? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var kegiatanList: ArrayList<Kegiatan>
    private var db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SessionManager(requireContext())
        username = sharedPref.getUsername

        setupView()
        setupRv()
        setUsername()
    }

    private fun setUsername() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("user").document(userId).get()
                .addOnSuccessListener { document ->
                    val username = document.getString("username")
                    val image = document.getString("photoUrl")

                    Glide.with(requireContext())
                        .load(image)
                        .into(binding.ivPhotoProfile)


                    binding?.tvUsername?.text = "Hallo, $username"
                }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {

    }


    private fun setupRv() {
        kegiatanList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        binding.progressBar.visibility = View.VISIBLE

        db.collection("kegiatan").get()
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                if (!it.isEmpty){
                    for (data in it.documents){
                        val kegiatan: Kegiatan? = data.toObject(Kegiatan::class.java)
                        if (kegiatan != null){
                            kegiatanList.add(kegiatan)
                        }
                    }
                    val adapter = KegiatanAdapter(kegiatanList)
                    binding.rvKegiatan.adapter = adapter
                    binding.rvKegiatan.layoutManager = LinearLayoutManager(requireContext())
                }
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
    }


}