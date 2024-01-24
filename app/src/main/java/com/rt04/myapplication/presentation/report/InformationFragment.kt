package com.rt04.myapplication.presentation.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.rt04.myapplication.core.data.Report
import com.rt04.myapplication.databinding.FragmentInformationBinding
import com.rt04.myapplication.presentation.adapter.ReportAdapter

class InformationFragment : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private lateinit var reportList: ArrayList<Report>
    private var db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
    }

    private fun setupRv() {
        reportList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        binding.progressBar.visibility = View.VISIBLE

        db.collection("laporan").get()
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val report: Report? = data.toObject(Report::class.java)
                        if (report != null) {
                            reportList.add(report)
                        }
                    }
                    val adapter = ReportAdapter(reportList)
                    binding.rvReport.adapter = adapter
                    binding.rvReport.layoutManager = LinearLayoutManager(requireContext())
                }
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

}