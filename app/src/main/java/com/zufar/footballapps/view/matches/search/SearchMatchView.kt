package com.zufar.footballapps.view.matches.search

import com.zufar.footballapps.model.Match

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchSearch(data: List<Match>)
}