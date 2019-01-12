package com.zufar.footballapps.view.matches

import com.google.gson.Gson
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.MatchResponse
import com.zufar.footballapps.model.MatchSearchResponse
import com.zufar.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getMatchList(match: String?,leagueId: String?) {
        view.showLoading()

        async (context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getMatch(match,leagueId)),
                    MatchResponse::class.java
                )
            }
            view.showMatchList(data.await().events)
            view.hideLoading()
        }
    }
}