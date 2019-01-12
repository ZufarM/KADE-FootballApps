package com.zufar.footballapps.view.teams

import com.zufar.footballapps.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}