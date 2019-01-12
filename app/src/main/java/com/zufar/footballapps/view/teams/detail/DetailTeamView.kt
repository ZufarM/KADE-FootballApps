package com.zufar.footballapps.view.teams.detail

import com.zufar.footballapps.model.Team

interface DetailTeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}