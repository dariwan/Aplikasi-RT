package com.rt04.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rt04.myapplication.core.data.Income
import com.rt04.myapplication.core.data.Kegiatan
import com.rt04.myapplication.databinding.FinanceKetuaListBinding
import com.rt04.myapplication.databinding.KegiatanKetuaListBinding

class IncomeRtAdapter(private val pemasukanList: ArrayList<Income>) : RecyclerView.Adapter<IncomeRtAdapter.MyViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Income, action: String)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder(val binding: FinanceKetuaListBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(pemasukan: Income) {
            binding.financeDate.text = pemasukan.tanggal
            binding.descFinance.text = pemasukan.nama
            binding.nominalFinance.text = pemasukan.jumlah.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FinanceKetuaListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pemasukanList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pemasukan = pemasukanList[position]
        holder.bind(pemasukan)
        holder.binding.btnHapus.setOnClickListener{
            onItemClickCallback.onItemClicked(pemasukanList[holder.adapterPosition], "hapus")
        }
    }
}