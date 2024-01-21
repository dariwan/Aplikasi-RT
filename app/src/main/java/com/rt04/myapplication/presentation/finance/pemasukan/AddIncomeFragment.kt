package com.rt04.myapplication.presentation.finance.pemasukan

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentAddIncomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class AddIncomeFragment : Fragment(), DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private lateinit var binding: FragmentAddIncomeBinding
    private val calendar = Calendar.getInstance()
    private val formatDate =  SimpleDateFormat("dd - MM - yyyy")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogDatePickerView()
        setupButton()
    }

    private fun setupButton() {
        binding.ivBack.setOnClickListener(this)
    }

    private fun dialogDatePickerView() {
        binding.dateImageButton.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(p1, p2, p3)
        displayFormatDate(calendar.timeInMillis)
    }

    private fun displayFormatDate(timeInMillis: Long) {
        binding.tvDate.text = formatDate.format(timeInMillis)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_back -> {
                findNavController().navigate(R.id.action_addIncomeFragment_to_reportIncomeFragment)
            }
        }
    }

}