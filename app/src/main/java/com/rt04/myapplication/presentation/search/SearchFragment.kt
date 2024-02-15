package com.rt04.myapplication.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rt04.myapplication.R
import com.rt04.myapplication.core.data.Search
import com.rt04.myapplication.databinding.FragmentSearchBinding
import com.rt04.myapplication.presentation.adapter.SearchAdapter
import java.util.Locale

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]

        searchData()
        setupRv()
    }

    private fun setupRv() {
        searchViewModel.searchResult.observe(viewLifecycleOwner) { result ->
            val adapter = SearchAdapter(result)
            binding.rvSearch.adapter = adapter
            binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
            adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Search) {
                    navigateToDetailFragment(data)
                }

            })
        }
    }

    private fun navigateToDetailFragment(data: Search) {
        val bundle = Bundle().apply {
            putParcelable(DATA_LIST, data)
        }
        findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
    }

    private fun searchData() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrBlank()) {
                    searchViewModel.search("", emptyArray(), emptyArray(), emptyArray(), emptyArray())
                } else {
                    val query = s.toString().lowercase(Locale.ROOT)
                    val title = resources.getStringArray(R.array.tittle)
                    val link = resources.getStringArray(R.array.link)
                    val adress = resources.getStringArray(R.array.adress)
                    val phone_number = resources.getStringArray(R.array.phone_number)
                    searchViewModel.search(query, title, link, adress, phone_number)
                }
            }

        })
    }

    companion object {
        const val DATA_LIST = "data_list"
    }

}