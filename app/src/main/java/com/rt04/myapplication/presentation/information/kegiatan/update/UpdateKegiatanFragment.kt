package com.rt04.myapplication.presentation.information.kegiatan.update

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
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Kegiatan
import com.rt04.myapplication.databinding.FragmentUpdateKegiatanBinding

class UpdateKegiatanFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentUpdateKegiatanBinding
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUpdateKegiatanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
        getData()
    }

    private fun setupButton() {
        binding.ivBack.setOnClickListener(this)
        binding.btnSimpan.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_back -> {
                findNavController().navigate(R.id.action_updateKegiatanFragment_to_kegiatanKetuaFragment)
            }
            R.id.btn_simpan -> {
                updateKategori()
            }
        }
    }

    private fun updateKategori() {
        val topik = binding.topikEditText.text.toString()
        val deskripsi = binding.penjelasanKegiatanEditText.text.toString()
        val tempat = binding.tempatEditText.text.toString()

        val updateMap = mapOf(
            "topik" to topik,
            "deskripsi" to deskripsi,
            "tempat" to tempat
        )

        binding.progressBar.visibility = View.VISIBLE
        val kegiatanId = arguments?.getString(EXTRA_ID)
        Log.e("update", "id add update ${kegiatanId}")

        if (kegiatanId != null) {
            db.collection("kegiatan").document(kegiatanId).update(updateMap)
                .addOnSuccessListener {
                    binding.progressBar.visibility = View.GONE

                    val bundle = Bundle().apply {
                        putString(EXTRA_ID, kegiatanId)
                    }


                    Toast.makeText(requireContext(), "Kegiatan berhasil diubah", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_updateKegiatanFragment_to_kegiatanKetuaFragment, bundle)
                }
                .addOnFailureListener {
                    binding.progressBar.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Kegiatan gagal diubah", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun getData() {
        val data = arguments?.getParcelable<Kegiatan>(EXTRA_NAME)
        Log.d("UpdateKegiatanFragment", "Data update: $data")
        data?.let {
            binding.tempatEditText.setText(it.tempat)
            binding.penjelasanKegiatanEditText.setText(it.deskripsi)
            binding.topikEditText.setText(it.topik)
        }
    }

    companion object {
        var EXTRA_NAME = "kegiatan"
        var EXTRA_ID = "kegiatanId"
    }
}