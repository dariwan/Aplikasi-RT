package com.rt04.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rt04.myapplication.core.data.Income
import com.rt04.myapplication.core.data.Spending
import com.rt04.myapplication.databinding.ReportFinanceListBinding

class FinanceSpendingAdapter(private val spendingList: ArrayList<Spending>): RecyclerView.Adapter<FinanceSpendingAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ReportFinanceListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(spending: Spending){
            val jumlah = spending.jumlah?.toInt()
            binding.nominalFinance.text = jumlah.toString()
            binding.descFinance.text = spending.nama
            binding.financeDate.text = spending.tanggal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ReportFinanceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return spendingList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val spending = spendingList[position]
        holder.bind(spending)
    }
}