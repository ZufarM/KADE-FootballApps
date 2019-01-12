package com.zufar.footballapps.api

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ApiRepositoryTest {

    @Test
    fun doRequest() {
        val apiRepos = mock(ApiRepository::class.java)
        // Url yang pertama kali di load dalam match league English Primere League (id = 4328)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepos.doRequest(url)
        Mockito.verify(apiRepos).doRequest(url)
    }
}