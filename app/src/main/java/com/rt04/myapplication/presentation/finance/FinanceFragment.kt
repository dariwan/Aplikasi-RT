package com.rt04.myapplication.presentation.finance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentFinanceBinding
import com.rt04.myapplication.presentation.finance.adapter.FinancePagerAdapter

class FinanceFragment : Fragment() {
    private lateinit var binding: FragmentFinanceBinding

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