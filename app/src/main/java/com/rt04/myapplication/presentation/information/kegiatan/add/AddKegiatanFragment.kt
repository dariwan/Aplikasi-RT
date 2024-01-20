package com.rt04.myapplication.presentation.information.kegiatan.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentAddKegiatanBinding

class AddKegiatanFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddKegiatanBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddKegiatanBinding.inflate(layoutInflater)
        return binding.root

        setupView()
    }

    private fun setupView() {

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){

        }
    }

}