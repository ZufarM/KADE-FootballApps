package com.zufar.footballapps.view.matches.detail

import com.google.gson.Gson
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.MatchResponse
import com.zufar.footballapps.model.TeamResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchPresenter (private val view: DetailMatchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson
){
    fun getDetailMatchList(idEvent: String?,idHome: String?, idAway: String?){
        view.showLoading()
        doAsync {
            val matchList = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchDetail(idEvent)),
                MatchResponse::class.java
            )

            val homeList = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(idHome)),
                TeamResponse::class.java
            )

            val awayList = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(idAway)),
                TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEventList(matchList.events,homeList.teams,awayList.teams)
            }
        }
    }
}