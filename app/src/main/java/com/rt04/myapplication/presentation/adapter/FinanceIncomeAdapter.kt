package com.rt04.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rt04.myapplication.core.data.Income
import com.rt04.myapplication.core.data.Kegiatan
import com.rt04.myapplication.databinding.ReportFinanceListBinding

class FinanceIncomeAdapter(private val incomeList: ArrayList<Income>): RecyclerView.Adapter<FinanceIncomeAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ReportFinanceListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(income: Income){
            binding.nominalFinance.text = income.jumlah.toString()
            binding.descFinance.text = income.nama
            binding.financeDate.text = income.tanggal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ReportFinanceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val income = incomeList[position]
        holder.bind(income)
    }
}