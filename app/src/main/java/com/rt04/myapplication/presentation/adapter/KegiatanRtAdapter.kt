package com.rt04.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rt04.myapplication.core.data.Kegiatan
import com.rt04.myapplication.databinding.KegiatanKetuaListBinding

class KegiatanRtAdapter(private val kegiatanList: ArrayList<Kegiatan> ): RecyclerView.Adapter<KegiatanRtAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: KegiatanKetuaListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(kegiatan: Kegiatan){
            binding.tvTittle.text = kegiatan.topik
            binding.tvDesc.text = kegiatan.deskripsi
            binding.tvTempat.text = kegiatan.tempat

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = KegiatanKetuaListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return kegiatanList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val kegiatan = kegiatanList[position]
        holder.bind(kegiatan)
    }
}