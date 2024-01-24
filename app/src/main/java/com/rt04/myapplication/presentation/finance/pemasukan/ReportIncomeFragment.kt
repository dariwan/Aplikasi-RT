package com.rt04.myapplication.presentation.finance.pemasukan

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
import com.rt04.myapplication.databinding.FragmentReportIncomeBinding
import com.rt04.myapplication.presentation.adapter.FinanceIncomeAdapter

class ReportIncomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentReportIncomeBinding
    private lateinit var incomeList: ArrayList<Income>
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReportIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
        setupRv()
    }

    private fun setupRv() {
        incomeList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        binding.progressBar.visibility = View.VISIBLE

        db.collection("pemasukan").get()
            .addOnSuccessListener { result ->
                binding.progressBar.visibility = View.GONE

                for (document in result) {
                    val income: Income = document.toObject(Income::class.java)
                    incomeList.add(income)
                }
                val adapter = FinanceIncomeAdapter(incomeList)
                binding.rvReport.adapter = adapter
                binding.rvReport.layoutManager = LinearLayoutManager(requireContext())
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupButton() {
        binding.buttonAdd.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.button_add -> {
                findNavController().navigate(R.id.action_reportIncomeFragment_to_addIncomeFragment)
            }

            R.id.iv_back -> {
                findNavController().navigate(R.id.action_reportIncomeFragment_to_financeFragment)
            }
        }
    }
}