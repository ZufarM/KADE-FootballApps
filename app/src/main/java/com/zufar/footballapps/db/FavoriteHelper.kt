package com.zufar.footballapps.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavoriteHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavouriteFB.db",null,1){
    companion object {
        private var instance: FavoriteHelper? = null

        @Synchronized
        fun getInstance(ctx:Context): FavoriteHelper {
            if (instance == null){
                instance = FavoriteHelper(ctx.applicationContext)
            }
            return instance as FavoriteHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(MatchFavorite.TABLE_MATCH, true,
            MatchFavorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            MatchFavorite.EVENT_ID to TEXT + UNIQUE,
            MatchFavorite.EVENT_NAME to TEXT,
            MatchFavorite.EVENT_DATE to TEXT,
            MatchFavorite.HOME_ID to TEXT,
            MatchFavorite.HOME_TEAM to TEXT,
            MatchFavorite.HOME_SCORE to TEXT,
            MatchFavorite.AWAY_ID to TEXT,
            MatchFavorite.AWAY_TEAM to TEXT,
            MatchFavorite.AWAY_SCORE to TEXT
        )
        db.createTable(TeamFavorite.TABLE_TEAM, true,
            TeamFavorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            TeamFavorite.TEAM_ID to TEXT + UNIQUE,
            TeamFavorite.TEAM_NAME to TEXT,
            TeamFavorite.TEAM_ICON to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MatchFavorite.TABLE_MATCH, true)
        db.dropTable(TeamFavorite.TABLE_TEAM, true)
    }
}

val Context.database: FavoriteHelper
    get() = FavoriteHelper.getInstance(applicationContext)