package com.rt04.myapplication.presentation.finance.pemasukan

import android.annotation.SuppressLint
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
import com.rt04.myapplication.databinding.FragmentIncomeBinding
import com.rt04.myapplication.presentation.adapter.FinanceIncomeAdapter

class IncomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentIncomeBinding
    private lateinit var incomeList: ArrayList<Income>
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
        setupRv()

        setupView()

    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        var income = 0.0

        calculateTotal("pemasukan") { calculatedIncome ->
            income = calculatedIncome

            val incomeWithoutDecimal = income.toInt()
            binding.tvNominalIncome.text = "Rp.${incomeWithoutDecimal}"
        }
    }


    private fun calculateTotal(collection: String, callback: (Double) -> Unit) {
        var total = 0.0
        db.collection(collection)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val jumlah = document.getDouble("jumlah")
                        jumlah.let {
                            if (it != null) {
                                total += it
                            }
                        }
                    }
                    callback(total)

                } else {
                    Toast.makeText(requireContext(), "Gagal ${task.exception}", Toast.LENGTH_SHORT)
                        .show()
                }

            }
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
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupButton() {
        binding.tvMore.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_more -> {
                findNavController().navigate(R.id.action_financeFragment_to_reportIncomeFragment)
            }
        }
    }


}