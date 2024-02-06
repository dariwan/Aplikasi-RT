package com.rt04.myapplication.presentation.finance.pengeluaran

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
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Income
import com.rt04.myapplication.core.data.Spending
import com.rt04.myapplication.databinding.FragmentReportSpendingKetuaBinding
import com.rt04.myapplication.presentation.adapter.IncomeRtAdapter
import com.rt04.myapplication.presentation.adapter.SpendingRtAdapter

class ReportSpendingKetuaFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentReportSpendingKetuaBinding
    private lateinit var financeList: ArrayList<Spending>
    private var db = Firebase.firestore
    private var selectedFinanceId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReportSpendingKetuaBinding.inflate(layoutInflater)
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

        db.collection("pengeluaran").get()
            .addOnSuccessListener { result ->
                binding.progressBar.visibility = View.GONE

                for (document in result) {
                    val pengeluaran: Spending = document.toObject(Spending::class.java)
                    pengeluaran.id = document.id
                    financeList.add(pengeluaran)
                }

                val adapter = SpendingRtAdapter(financeList)
                binding.rvSpendingKetua.adapter = adapter
                binding.rvSpendingKetua.layoutManager = LinearLayoutManager(requireContext())
                adapter.setOnItemClickCallback(object: SpendingRtAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: Spending, action: String) {
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
        binding.progressBar.visibility = View.VISIBLE

        val pemasukanId = selectedFinanceId

        if (pemasukanId != null){
            db.collection("pengeluaran").document(pemasukanId).delete()
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


    private fun setupButton() {
        binding.btnAdd.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_add -> {
                findNavController().navigate(R.id.action_reportSpendingKetuaFragment_to_addSpendingFragment)
            }

            R.id.iv_back -> {
                findNavController().navigate(R.id.action_reportSpendingKetuaFragment_to_profileFragment)
            }
        }


    }
}