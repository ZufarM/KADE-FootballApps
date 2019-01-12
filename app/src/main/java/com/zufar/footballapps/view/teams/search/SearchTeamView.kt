package com.zufar.footballapps.view.teams.search

import com.zufar.footballapps.model.Team

interface SearchTeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamSearch(data: List<Team>)
}