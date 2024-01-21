package com.rt04.myapplication.presentation.finance.pengeluaran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Spending
import com.rt04.myapplication.databinding.FragmentSpendingBinding
import com.rt04.myapplication.presentation.adapter.FinanceSpendingAdapter

class SpendingFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentSpendingBinding
    private lateinit var spendingList: ArrayList<Spending>
    private var db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSpendingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
        setupRv()
    }

    private fun setupButton() {
        binding.tvMore.setOnClickListener(this)
        binding
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.tv_more -> {
                findNavController().navigate(R.id.action_financeFragment_to_reportSpendingFragment)
            }
        }
    }

    private fun setupRv() {
        spendingList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        binding.progressBar.visibility = View.VISIBLE

        db.collection("pengeluaran").get()
            .addOnSuccessListener { result ->
                binding.progressBar.visibility = View.GONE

                for (document in result){
                    val spending: Spending? = document.toObject(Spending::class.java)
                    if (spending != null){
                        //id

                        spendingList.add(spending)
                    }
                }
                val adapter = FinanceSpendingAdapter(spendingList)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
    }


}