package com.zufar.footballapps.view.matches

import com.zufar.footballapps.model.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
}