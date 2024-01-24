package com.rt04.myapplication.presentation.information.kegiatan.add

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Kegiatan
import com.rt04.myapplication.databinding.FragmentKegiatanKetuaBinding
import com.rt04.myapplication.presentation.adapter.KegiatanRtAdapter
import com.rt04.myapplication.presentation.information.kegiatan.update.UpdateKegiatanFragment.Companion.EXTRA_ID
import com.rt04.myapplication.presentation.information.kegiatan.update.UpdateKegiatanFragment.Companion.EXTRA_NAME

class KegiatanKetuaFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentKegiatanKetuaBinding
    private lateinit var kegiatanList: ArrayList<Kegiatan>
    private var db = Firebase.firestore
    private var selectedKegiatanId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentKegiatanKetuaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
        setupRv()

    }

    private fun setupRv() {
        kegiatanList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        binding.progressBar.visibility = View.VISIBLE

        db.collection("kegiatan").get()
            .addOnSuccessListener { result ->
                binding.progressBar.visibility = View.GONE

                for (document in result) {
                    val kegiatan: Kegiatan = document.toObject(Kegiatan::class.java)
                    kegiatan.id = document.id
                    kegiatanList.add(kegiatan)
                }
                val adapter = KegiatanRtAdapter(kegiatanList)
                binding.rvKegiatanKetua.adapter = adapter
                binding.rvKegiatanKetua.layoutManager = LinearLayoutManager(requireContext())
                adapter.setOnItemClickCallback(object : KegiatanRtAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Kegiatan, action: String) {
                        when (action) {
                            "edit" -> {
                                selectedKegiatanId = data.id
                                navigateToUpdateFragment(data)
                            }

                            "hapus" -> {
                                selectedKegiatanId = data.id
                                hapusData()
                            }
                        }
                    }

                })

            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun hapusData() {
        binding.progressBar.visibility = View.VISIBLE

        val kegiatanId = selectedKegiatanId
        Log.e("hapus", "id: $kegiatanId")

        if (kegiatanId != null) {
            db.collection("kegiatan").document(kegiatanId).delete()
                .addOnSuccessListener {
                    binding.progressBar.visibility = View.GONE
                    setupRv()
                    Toast.makeText(
                        requireContext(),
                        "Kegiatan berhasil dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Gagal hapus kegiatan", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun navigateToUpdateFragment(data: Kegiatan) {
        val bundle = Bundle().apply {
            putParcelable(EXTRA_NAME, data)
            putString(EXTRA_ID, data.id)
            Log.e("hapus", "${data.id}")
        }
        Log.d("UpdateKegiatanFragment", "Data kegiatan: $data")

        findNavController().navigate(
            R.id.action_kegiatanKetuaFragment_to_updateKegiatanFragment,
            bundle
        )
    }

    private fun setupButton() {
        binding.btnAdd.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_add -> {
                findNavController().navigate(R.id.action_kegiatanKetuaFragment_to_addKegiatanFragment)
            }

            R.id.iv_back -> {
                findNavController().navigate(R.id.action_kegiatanKetuaFragment_to_profileFragment)
            }
        }
    }


}