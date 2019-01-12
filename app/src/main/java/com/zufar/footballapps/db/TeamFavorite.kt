package com.zufar.footballapps.db

class TeamFavorite (val id: Long?,
                    val teamId: String?,
                    val teamName: String?,
                    val teamIcon: String?) {

    companion object {
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_ICON: String = "TEAM_ICON"
    }
}