package com.zufar.footballapps.view.matches.detail

import com.zufar.footballapps.model.Match
import com.zufar.footballapps.model.Team

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(
        eventMatch: List<Match>,
        homeTeam: List<Team>,
        awayTeam: List<Team>
    )
}