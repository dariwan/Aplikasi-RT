package com.rt04.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rt04.myapplication.core.data.Report
import com.rt04.myapplication.databinding.ReportListBinding

class ReportAdapter(private val reportList: ArrayList<Report>): RecyclerView.Adapter<ReportAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ReportListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(report: Report){
            binding.tvTittle.text = report.topik
            binding.tvDesc.text = report.masalah
            binding.tvNama.text = report.nama
            Glide.with(itemView.context)
                .load(report.image)
                .into(binding.ivLaporan)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ReportListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val report = reportList[position]
        holder.bind(report)
    }
}