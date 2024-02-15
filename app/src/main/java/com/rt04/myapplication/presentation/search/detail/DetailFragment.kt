package com.rt04.myapplication.presentation.search.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Search
import com.rt04.myapplication.databinding.FragmentDetailBinding
import com.rt04.myapplication.presentation.search.SearchFragment.Companion.DATA_LIST

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDetailData()
        setupButton()
    }

    private fun setupButton() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_searchFragment)
        }
    }

    private fun setupDetailData() {
        val data = arguments?.getParcelable<Search>(DATA_LIST)
        data.let {
            binding.tvTittle.text = it?.tittle
            binding.tvAddress.text = "Alamat: ${it?.adress}"
            binding.tvPhoneNumber.text = "Telephone: ${it?.phone_number}"
            val url = it?.link
            binding.btnToWeb.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
        }
    }
}