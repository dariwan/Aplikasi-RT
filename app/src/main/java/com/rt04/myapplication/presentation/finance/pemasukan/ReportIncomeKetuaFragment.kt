package com.rt04.myapplication.presentation.finance.pemasukan

import android.app.AlertDialog
import android.os.Bundle
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
import com.google.firebase.firestore.toObject
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Income
import com.rt04.myapplication.databinding.FragmentReportIncomeKetuaBinding
import com.rt04.myapplication.presentation.adapter.IncomeRtAdapter

class ReportIncomeKetuaFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentReportIncomeKetuaBinding
    private lateinit var financeList: ArrayList<Income>
    private var db = Firebase.firestore
    private var selectedFinanceId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReportIncomeKetuaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
        setupRv()
    }

    private fun setupRv() {
        financeList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        binding.progressBar.visibility = View.VISIBLE

        db.collection("pemasukan").get()
            .addOnSuccessListener { result ->
                binding.progressBar.visibility = View.GONE

                for (document in result) {
                    val pemasukan: Income = document.toObject(Income::class.java)
                    pemasukan.id = document.id
                    financeList.add(pemasukan)
                }

                val adapter = IncomeRtAdapter(financeList)
                binding.rvIncomeKetua.adapter = adapter
                binding.rvIncomeKetua.layoutManager = LinearLayoutManager(requireContext())
                adapter.setOnItemClickCallback(object: IncomeRtAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: Income, action: String) {
                        when(action){
                            "hapus" -> {
                                selectedFinanceId = data.id
                                hapusData()
                            }
                        }
                    }

                })
            }

    }

    private fun hapusData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi Hapus?")
        builder.setMessage("Apakah Anda yakin ingin menghapus data ini?")

        builder.setPositiveButton("Hapus"){ dialog, which ->
            binding.progressBar.visibility = View.VISIBLE

            val pemasukanId = selectedFinanceId

            if (pemasukanId != null){
                db.collection("pemasukan").document(pemasukanId).delete()
                    .addOnSuccessListener {
                        binding.progressBar.visibility = View.GONE
                        setupRv()
                        Toast.makeText(
                            requireContext(),
                            "Berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Gagal hapus", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }

        builder.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    private fun setupButton() {
        binding.btnAdd.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_add -> {
                findNavController().navigate(R.id.action_reportIncomeKetuaFragment_to_addIncomeFragment)
            }

            R.id.iv_back -> {
                findNavController().navigate(R.id.action_reportIncomeKetuaFragment_to_profileFragment)
            }
        }
    }
}