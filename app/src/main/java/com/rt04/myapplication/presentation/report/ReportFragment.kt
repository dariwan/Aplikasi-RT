package com.rt04.myapplication.presentation.report

import android.os.Bundle
import android.util.Log
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
import com.rt04.myapplication.core.data.Report
import com.rt04.myapplication.databinding.FragmentReportBinding
import com.rt04.myapplication.presentation.adapter.ReportUserAdapter
import com.rt04.myapplication.presentation.report.update.UpdateReportFragment.Companion.ID
import com.rt04.myapplication.presentation.report.update.UpdateReportFragment.Companion.NAME

class ReportFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentReportBinding
    private lateinit var reportList: ArrayList<Report>
    private var db = Firebase.firestore
    private var selectedReportId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupButton()
        setupRv()
    }

    private fun setupRv() {
        reportList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        binding.progressBar.visibility = View.VISIBLE

        db.collection("laporan").get()
            .addOnSuccessListener { result ->
                binding.progressBar.visibility = View.GONE

                for (document in result) {
                    val report: Report = document.toObject(Report::class.java)
                    report.id = document.id
                    reportList.add(report)
                }
                val adapter = ReportUserAdapter(reportList)
                binding.rvLaporan.adapter = adapter
                binding.rvLaporan.layoutManager = LinearLayoutManager(requireContext())
                adapter.setOnItemClickCallback(object : ReportUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Report, action: String) {
                        when (action) {
                            "edit" -> {
                                selectedReportId = data.id
                                navigateToUpdateFragment(data)
                            }

                            "hapus" -> {
                                selectedReportId = data.id
                                hapusData()
                            }
                        }
                    }

                })

            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun hapusData() {
        binding.progressBar.visibility = View.VISIBLE

        val reportId = selectedReportId
        Log.e("hapus", "id: $reportId")

        if (reportId != null) {
            db.collection("laporan").document(reportId).delete()
                .addOnSuccessListener {
                    binding.progressBar.visibility = View.GONE
                    setupRv()
                    Toast.makeText(
                        requireContext(),
                        "Kegiatan berhasil dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Gagal hapus kegiatan", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun navigateToUpdateFragment(data: Report) {
        val bundle = Bundle().apply {
            putParcelable(NAME, data)
            putString(ID, data.id)
            Log.e("hapus", "${data.id}")
        }
        Log.d("UpdateKegiatanFragment", "Data kegiatan: $data")

        findNavController().navigate(R.id.action_reportFragment_to_updateReportFragment, bundle)
    }

    private fun setupButton() {
        binding.btnAddLaporan.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_add_laporan -> {
                findNavController().navigate(R.id.action_reportFragment_to_addReportFragment)
            }

            R.id.iv_back -> {
                findNavController().navigate(R.id.action_reportFragment_to_profileFragment)
            }
        }
    }
}