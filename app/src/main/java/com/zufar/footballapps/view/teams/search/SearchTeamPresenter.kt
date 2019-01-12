package com.zufar.footballapps.view.teams.search

import com.google.gson.Gson
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.TeamResponse
import com.zufar.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchTeamPresenter (private val view: SearchTeamView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun searchTeamList (key: String?) {
        view.showLoading()

        async (context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeamSearch(key)),
                    TeamResponse::class.java
                )
            }
            view.showTeamSearch(data.await().teams)
            view.hideLoading()
        }
    }
}