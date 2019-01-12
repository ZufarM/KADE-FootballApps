package com.zufar.footballapps.view.teams.detail

import com.google.gson.Gson
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.TeamResponse
import com.zufar.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailTeamPresenter(private val view: DetailTeamView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailTeam(teamId: String) {
        view.showLoading()

        async(contextPool.main){
            val data = bg{
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                    TeamResponse::class.java
                )
            }

            view.showTeamDetail(data.await().teams)
            view.hideLoading()
        }
    }
}