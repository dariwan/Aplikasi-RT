package com.rt04.myapplication.presentation.information.kegiatan.add

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentAddKegiatanBinding
import com.rt04.myapplication.presentation.information.kegiatan.update.UpdateKegiatanFragment.Companion.EXTRA_ID

class AddKegiatanFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddKegiatanBinding
    private var db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddKegiatanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.btnSimpan.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_simpan -> {
                buatKegiatan()
            }
        }
    }

    private fun buatKegiatan() {
        val topik = binding.topikEditText.text.toString()
        val deskripsi = binding.penjelasanKegiatanEditText.text.toString()
        val tempat = binding.tempatEditText.text.toString()

        val kegiatan = hashMapOf(
            "topik" to topik,
            "deskripsi" to deskripsi,
            "tempat" to tempat
        )

        binding.progressBar.visibility = View.VISIBLE

        db.collection("kegiatan")
            .add(kegiatan)
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "kegiatan berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addKegiatanFragment_to_kegiatanKetuaFragment)
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "gagal ditambahkan", Toast.LENGTH_SHORT).show()
            }

    }

}