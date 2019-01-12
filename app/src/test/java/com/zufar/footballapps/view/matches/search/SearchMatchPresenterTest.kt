package com.zufar.footballapps.view.matches.search

import com.google.gson.Gson
import com.zufar.footballapps.TestContextProvider
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.Match
import com.zufar.footballapps.model.MatchSearchResponse
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest {
    @Mock
    private
    lateinit var view: SearchMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var presenter: SearchMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = SearchMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun searchMatchList() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchSearchResponse(match)
        val key = "barcelona"

        match.clear()

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getMatchSearch(key)),
                MatchSearchResponse::class.java
            )
        ).thenReturn(response)

        presenter.searchMatchList(key)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchSearch(match)
        Mockito.verify(view).hideLoading()
    }
}