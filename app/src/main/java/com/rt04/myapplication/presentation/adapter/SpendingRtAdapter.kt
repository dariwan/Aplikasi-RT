package com.rt04.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rt04.myapplication.core.data.Income
import com.rt04.myapplication.core.data.Spending
import com.rt04.myapplication.databinding.FinanceKetuaListBinding

class SpendingRtAdapter(private val pengeluaranList: ArrayList<Spending>): RecyclerView.Adapter<SpendingRtAdapter.MyViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Spending, action: String)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder(val binding: FinanceKetuaListBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(pengeluaran: Spending) {
            binding.financeDate.text = pengeluaran.tanggal
            binding.descFinance.text = pengeluaran.nama
            binding.nominalFinance.text = pengeluaran.jumlah.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FinanceKetuaListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pengeluaranList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pengeluaran = pengeluaranList[position]
        holder.bind(pengeluaran)
        holder.binding.btnHapus.setOnClickListener{
            onItemClickCallback.onItemClicked(pengeluaranList[holder.adapterPosition], "hapus")
        }
    }
}