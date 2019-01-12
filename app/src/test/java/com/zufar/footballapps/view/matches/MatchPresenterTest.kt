package com.zufar.footballapps.view.matches

import com.google.gson.Gson
import com.zufar.footballapps.TestContextProvider
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.Match
import com.zufar.footballapps.model.MatchResponse
import com.zufar.footballapps.model.MatchSearchResponse
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchPresenterTest {
    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var presenter: MatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchListTest() {
        val matchs: MutableList<Match> = mutableListOf()
        val response = MatchResponse(matchs)
        val event = "past"
        val leagueId = "4328"

        matchs.clear()

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getMatch(event,leagueId)),
                MatchResponse::class.java
            )
        ).thenReturn(response)

        presenter.getMatchList(event,leagueId)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(matchs)
        Mockito.verify(view).hideLoading()
    }
}