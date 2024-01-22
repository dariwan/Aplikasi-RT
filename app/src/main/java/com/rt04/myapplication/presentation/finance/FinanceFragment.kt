package com.rt04.myapplication.presentation.finance

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentFinanceBinding
import com.rt04.myapplication.presentation.finance.adapter.FinancePagerAdapter

class FinanceFragment : Fragment() {
    private lateinit var binding: FragmentFinanceBinding
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFinanceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()
        setupView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        var income = 0.0
        var spending = 0.0

        calculateTotal("pemasukan") { calculatedIncome ->
            income = calculatedIncome

            calculateTotal("pengeluaran") { calculatedSpending ->
                spending = calculatedSpending

                val totalWithoutDecimal = (income - spending).toInt()
                binding.tvTotalFinance.text = "Total: ${totalWithoutDecimal}"
            }
        }
    }

    private fun calculateTotal(collection: String, callback: (Double) -> Unit){
        var total = 0.0
        db.collection(collection)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    for (document in task.result){
                        val jumlah = document.getDouble("jumlah")
                        jumlah.let {
                            if (it != null) {
                                total += it
                            }
                        }
                    }
                    callback(total)

                } else{
                    Toast.makeText(requireContext(), "Gagal ${task.exception}", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun setupTabLayout() {
        val bundle = Bundle()
        val sectionsPagerAdapter = FinancePagerAdapter(requireContext(), childFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }


}