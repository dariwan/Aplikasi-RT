package com.rt04.myapplication.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.core.utils.SessionManager
import com.rt04.myapplication.databinding.FragmentProfileBinding
import com.rt04.myapplication.presentation.register.RegisterActivity

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: SessionManager
    private var username: String? = null
    private var category: String? = null
    private var email: String? = null
    private var db = Firebase.firestore

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
        Log.e("profile", "$category")

        setupView()
        setData()

    }

    private fun setData() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("user").document(userId).get()
            .addOnSuccessListener { document ->
                val username = document.getString("username")
                val email = document.getString("email")
                val role = document.getString("category")
                binding.tvUsername.text = username
                binding.tvEmail.text = email
                binding.tvCategory.text = role
            }
    }

    private fun setupView() {
        binding.btnAdd.setOnClickListener(this)
        binding.btnLaporan.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)


        if (category == "Warga"){
            binding.btnAdd.visibility = View.GONE
        }else{
            binding.btnAdd.visibility = View.VISIBLE
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_add ->{
                findNavController().navigate(R.id.action_profileFragment_to_kegiatanKetuaFragment)
            }
            R.id.btn_laporan -> {
                findNavController().navigate(R.id.action_profileFragment_to_reportFragment)
            }
            R.id.btn_logout -> {
                sharedPref.clearData()
                val intent = Intent(requireContext(), RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

}