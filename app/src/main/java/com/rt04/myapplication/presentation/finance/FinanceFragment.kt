package com.rt04.myapplication.presentation.finance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private fun setupView() {

    }

    private fun calculateTotal(collection: String){
        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                var total = 0
                for (document in result){
                    val jumlahInt = document
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