package com.rt04.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rt04.myapplication.core.data.Kegiatan
import com.rt04.myapplication.databinding.KegiatanKetuaListBinding
import com.rt04.myapplication.databinding.KegiatanListBinding

class KegiatanAdapter(private val kegiatanList: ArrayList<Kegiatan> ): RecyclerView.Adapter<KegiatanAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: KegiatanListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(kegiatan: Kegiatan){
            binding.tvTittle.text = kegiatan.topik
            binding.tvDesc.text = kegiatan.deskripsi
            binding.tvTempat.text = kegiatan.tempat

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = KegiatanListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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