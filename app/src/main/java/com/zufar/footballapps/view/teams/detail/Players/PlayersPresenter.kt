package com.zufar.footballapps.view.teams.detail.Players

import com.google.gson.Gson
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.PlayerDetailResponse
import com.zufar.footballapps.model.PlayerResponse
import com.zufar.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayersPresenter (private val view: PlayersView,
                        private val apiRepository: ApiRepository,
                        private val gson: Gson,
                        private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerList(teamId: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getAllPlayerByTeamId(teamId)),
                    PlayerResponse::class.java
                )
            }
            view.showPlayerList(data.await().player)
            view.hideLoading()
        }
    }

    fun getDetailPlayer(playerId: String?){
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPlayerDetail(playerId)),
                    PlayerDetailResponse::class.java
                )
            }
            view.showPlayerList(data.await().players)
            view.hideLoading()
        }
    }
}