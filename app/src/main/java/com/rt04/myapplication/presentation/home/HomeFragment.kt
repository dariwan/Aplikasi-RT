package com.rt04.myapplication.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUsername()
        setupButton()
    }

    private fun setupButton() {
        binding.cardFinanceInformation.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_financeFragment)
            findNavController().navigate(R.id.financeFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.homeFragment, true)
                    .build())
        }
        binding.cardActivitiesInformation.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_kegiatanFragment)
        }
        binding.cardEnvironmentalInformation.setOnClickListener {
            findNavController().navigate(R.id.informationFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.homeFragment, true)
                    .build())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUsername() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("user").document(userId).get()
                .addOnSuccessListener { document ->
                    val username = document.getString("username")
                    val image = document.getString("photoUrl")

                    Glide.with(requireContext())
                        .load(image)
                        .into(binding.ivPhotoProfile)

                    binding.tvUsername.text = "Hallo, $username"
                }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}