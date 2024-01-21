package com.rt04.myapplication.presentation.finance.pemasukan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentReportIncomeBinding

class ReportIncomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentReportIncomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentReportIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
    }

    private fun setupButton() {
        binding.buttonAdd.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.button_add -> {
                findNavController().navigate(R.id.action_reportIncomeFragment_to_addIncomeFragment)
            }
            R.id.iv_back -> {
                findNavController().navigate(R.id.action_reportIncomeFragment_to_financeFragment)
            }
        }
    }
}