package com.rt04.myapplication.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rt04.myapplication.R
import com.rt04.myapplication.core.utils.SessionManager
import com.rt04.myapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: SessionManager
    private var username: String? = null
    private var category: String? = null
    private var email: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SessionManager(requireContext())
        username = sharedPref.getUsername
        category = sharedPref.getRole
        email = sharedPref.getEmail

        setupView()

    }

    private fun setupView() {
        binding.tvUsername.text = username
        binding.tvEmail.text = email
        binding.tvCategory.text = category

        if (category == "Warga"){
            binding.btnAdd.visibility = View.GONE
        }else{
            binding.btnAdd.visibility = View.VISIBLE
        }
    }

}