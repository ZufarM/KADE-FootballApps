package com.zufar.footballapps.view.matches.search

import com.google.gson.Gson
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.MatchSearchResponse
import com.zufar.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchMatchPresenter(private val view: SearchMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun searchMatchList(key: String?) {
        view.showLoading()

        async (context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getMatchSearch(key)),
                    MatchSearchResponse::class.java
                )
            }
            view.showMatchSearch(data.await().event)
            view.hideLoading()
        }
    }
}