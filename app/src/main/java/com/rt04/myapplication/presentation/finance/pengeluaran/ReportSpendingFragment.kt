package com.rt04.myapplication.presentation.finance.pengeluaran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentReportSpendingBinding

class ReportSpendingFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentReportSpendingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentReportSpendingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
    }

    private fun setupButton() {
        binding.ivBack.setOnClickListener(this)
        binding.buttonAdd.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_back ->{
                findNavController().navigate(R.id.action_reportSpendingFragment_to_financeFragment)
            }
            R.id.button_add -> {
                findNavController().navigate(R.id.action_reportSpendingFragment_to_addSpendingFragment)
            }
        }
    }
}