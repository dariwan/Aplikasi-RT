package com.rt04.myapplication.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rt04.myapplication.R
import com.rt04.myapplication.core.utils.SessionManager
import com.rt04.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var sharedPref: SessionManager
    private var username: String? = null
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeComponent()
        sharedPref = SessionManager(requireContext())
        username = sharedPref.getUsername

        setupView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        binding.tvUsername.text = "Hallo, $username"
    }

    private fun initializeComponent() {
        binding.cvInformasiKegiatan.setOnClickListener(this)
        binding.cvInformasiKeuangan.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.cv_informasi_kegiatan -> {
                findNavController().navigate(R.id.action_homeFragment_to_kegiatanFragment)
            }
            R.id.cv_informasi_keuangan ->{
                findNavController().navigate(R.id.action_homeFragment_to_financeFragment)
            }
        }
    }


}