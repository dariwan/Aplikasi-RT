package com.rt04.myapplication.presentation.information.kegiatan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentKegiatanBinding

class KegiatanFragment : Fragment() {

    private lateinit var binding: FragmentKegiatanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentKegiatanBinding.inflate(layoutInflater)
        return binding.root
    }


}