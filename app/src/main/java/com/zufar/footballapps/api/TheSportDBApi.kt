package com.zufar.footballapps.api

import com.zufar.footballapps.BuildConfig

object TheSportDBApi {

    // Match Link
    fun getMatch(match: String?,leagueId: String?): String{
        // https://www.thesportsdb.com/api/v1/json/1/events..league.php?id=..
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/events"+match+"league.php?id=" + leagueId
    }

    fun getMatchDetail(matchId: String?): String {
        // https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=...
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + matchId
    }

    fun getMatchSearch(searchEvent: String?): String {
        // https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e=..
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchevents.php?e=" + searchEvent
    }

    // Team Link
    fun getTeams(league: String?): String {
        // https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=..
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
    }

    fun getTeamDetail(teamId: String?): String{
        // https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=..
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    }
    fun getTeamSearch(searchTeam: String?): String {
        //https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=..
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchteams.php?t=" + searchTeam
    }

    // Player Link
    fun getAllPlayerByTeamId(teamId: String?): String {
        // https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id=..
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + teamId
    }

    fun getPlayerDetail(playerId: String?) : String {
        // https://www.thesportsdb.com/api/v1/json/1/lookupplayer.php?id=..
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupplayer.php?id=" + playerId
    }
}