package com.rt04.myapplication.presentation.information.kegiatan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Kegiatan
import com.rt04.myapplication.databinding.FragmentKegiatanBinding
import com.rt04.myapplication.presentation.adapter.KegiatanAdapter
import com.rt04.myapplication.presentation.adapter.KegiatanRtAdapter

class KegiatanFragment : Fragment() {

    private lateinit var binding: FragmentKegiatanBinding
    private lateinit var kegiatanList: ArrayList<Kegiatan>
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentKegiatanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()

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