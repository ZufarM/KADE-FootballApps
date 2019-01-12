package com.zufar.footballapps.view.teams.search

import com.google.gson.Gson
import com.zufar.footballapps.TestContextProvider
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.Team
import com.zufar.footballapps.model.TeamResponse
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchTeamPresenterTest {

    @Mock
    private
    lateinit var view: SearchTeamView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var presenter: SearchTeamPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = SearchTeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun searchTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val key = "barcelona"

        teams.clear()
        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getMatchSearch(key)),
                TeamResponse::class.java
            )
        ).thenReturn(response)

        presenter.searchTeamList(key)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamSearch(teams)
        Mockito.verify(view).hideLoading()
    }
}