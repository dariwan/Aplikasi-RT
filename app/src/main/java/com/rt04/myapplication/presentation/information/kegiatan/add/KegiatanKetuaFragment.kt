package com.rt04.myapplication.presentation.information.kegiatan.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentKegiatanKetuaBinding

class KegiatanKetuaFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentKegiatanKetuaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentKegiatanKetuaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

    }

    private fun setupView() {
        binding.btnAdd.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_add -> {
                findNavController().navigate(R.id.action_kegiatanKetuaFragment_to_addKegiatanFragment)
            }
        }
    }


}