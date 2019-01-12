package com.zufar.footballapps.view.teams

import com.google.gson.Gson
import com.zufar.footballapps.TestContextProvider
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.api.TheSportDBApi
import com.zufar.footballapps.model.Team
import com.zufar.footballapps.model.TeamResponse
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamPresenterTest {

    @Mock
    private
    lateinit var view: TeamView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var presenter: TeamPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val league = "English_Premiere_League"
        teams.clear()

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeams(league)),
                TeamResponse::class.java
            )
        ).thenReturn(response)

        presenter.getTeamList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(teams)
        Mockito.verify(view).hideLoading()
    }

//    @Test
//    fun searchTeamList() {
//        val teams: MutableList<Team> = mutableListOf()
//        val response = TeamResponse(teams)
//        val key = "barcelona"
//
//        teams.clear()
//        Mockito.`when`(
//            gson.fromJson(
//                apiRepository
//                    .doRequest(TheSportDBApi.getMatchSearch(key)),
//                TeamResponse::class.java
//            )
//        ).thenReturn(response)
//
//        presenter.searchTeamList(key)
//
//        Mockito.verify(view).showLoading()
//        Mockito.verify(view).showTeamList(teams)
//        Mockito.verify(view).hideLoading()
//    }
}