package com.zufar.footballapps.view.teams.detail.Players

import com.zufar.footballapps.model.Player

interface PlayersView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}