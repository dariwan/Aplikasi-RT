package com.rt04.myapplication.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rt04.myapplication.core.data.Search

class SearchViewModel : ViewModel() {
    val searchResult = MutableLiveData<List<Search>>()

    fun search(query: String, titles: Array<String>, links: Array<String>, adress: Array<String>, phone_number: Array<String>) {
        val lowerCaseQuery = query.toLowerCase()
        val result = mutableListOf<Search>()

        if (query.isNotBlank()) {
            for (i in titles.indices) {
                val lowerCaseTittle = titles[i].toLowerCase()
                if (lowerCaseTittle.contains(lowerCaseQuery)) {
                    result.add(Search(titles[i], links[i], adress[i], phone_number[i]))
                }
            }
        }
        searchResult.value = result
    }
}