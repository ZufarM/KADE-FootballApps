package com.zufar.footballapps.view.matches.detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.zufar.footballapps.R
import com.zufar.footballapps.R.drawable.ic_add_to_favorites
import com.zufar.footballapps.R.drawable.ic_added_to_favorites
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.db.MatchFavorite
import com.zufar.footballapps.db.database
import com.zufar.footballapps.model.Match
import com.zufar.footballapps.model.Team
import kotlinx.android.synthetic.main.abc_alert_dialog_material.*
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {

    private lateinit var presenter: DetailMatchPresenter
    private var match: MutableList<Match> = mutableListOf()
    private var home: MutableList<Team> = mutableListOf()
    private var away: MutableList<Team> = mutableListOf()

    private lateinit var idEvent: String
    private lateinit var idHome: String
    private lateinit var idAway: String

    private var menuItem: Menu? = null
    private var isFavourite: Boolean = false
    private lateinit var matchf: Match
    private lateinit var datef: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idEvent = intent.getStringExtra("id")
        idHome = intent.getStringExtra("homeId")
        idAway = intent.getStringExtra("awayId")

        // Request to API
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailMatchPresenter(this, request, gson)
        presenter.getDetailMatchList(idEvent, idHome, idAway)

        favouriteState()
    }

    private fun favouriteState(){
        database.use{
            val result = select(MatchFavorite.TABLE_MATCH)
                .whereArgs("(EVENT_ID = {id})",
                    "id" to idEvent)
            val favourites = result.parseList(classParser<MatchFavorite>())
            if (!favourites.isEmpty()) isFavourite = true
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showEventList(eventMatch: List<Match>, homeTeam: List<Team>, awayTeam: List<Team>) {
        // Adding data to sub list
        match.clear()
        match.addAll(eventMatch)
        home.clear()
        home.addAll(homeTeam)
        away.clear()
        away.addAll(awayTeam)

        val date = findViewById<TextView>(R.id.tv_date)

        val homeImg = findViewById<ImageView>(R.id.img_home_team)
        val homeTxt = findViewById<TextView>(R.id.tv_home_team)
        val homeScr = findViewById<TextView>(R.id.tv_scr_home)
        val homeGoals = findViewById<TextView>(R.id.tv_home_goals)
        // lineups
        val homeGoalKeeper = findViewById<TextView>(R.id.tv_home_goalskeeper)
        val homeDefense = findViewById<TextView>(R.id.tv_home_def)
        val homeMidefield = findViewById<TextView>(R.id.tv_home_mid)

        val awayImg = findViewById<ImageView>(R.id.img_home_away)
        val awayTxt = findViewById<TextView>(R.id.tv_away_team)
        val awayScr = findViewById<TextView>(R.id.tv_scr_away)
        val awayGoals = findViewById<TextView>(R.id.tv_away_goals)
        // lineups
        val awayGoalKeeper = findViewById<TextView>(R.id.tv_away_goalskeeper)
        val awayDefense = findViewById<TextView>(R.id.tv_away_def)
        val awayMidefield = findViewById<TextView>(R.id.tv_away_mid)

        date.text = eventMatch[0].getDate2()
        datef = eventMatch[0].getDate2()
        Picasso.get()
            .load(homeTeam[0].strTeamBadge)
            .into(homeImg)
        Picasso.get()
            .load(awayTeam[0].strTeamBadge)
            .into(awayImg)

        homeTxt.text = eventMatch[0].strHomeTeam
        homeScr.text = eventMatch[0].intHomeScore
        homeGoals.text = eventMatch[0].strHomeGoalDetails
        // lineups
        homeGoalKeeper.text = eventMatch[0].strHomeLineupGoalkeeper
        homeDefense.text = eventMatch[0].strHomeLineupDefense
        homeMidefield.text = eventMatch[0].strHomeLineupMidfield

        awayTxt.text = eventMatch[0].strAwayTeam
        awayScr.text = eventMatch[0].intAwayScore
        awayGoals.text = eventMatch[0].strAwayGoalDetails
        // lineups
        awayGoalKeeper.text = eventMatch[0].strAwayLineupGoalkeeper
        awayDefense.text = eventMatch[0].strAwayLineupDefense
        awayMidefield.text = eventMatch[0].strAwayLineupMidfield

        matchf = Match(
            eventMatch[0].idEvent,
            eventMatch[0].dateEvent,
            eventMatch[0].strDate,
            eventMatch[0].strTime,
            eventMatch[0].strEvent,
            // home
            eventMatch[0].idHomeTeam,
            eventMatch[0].strHomeTeam,
            eventMatch[0].intHomeScore,
            eventMatch[0].strHomeGoalDetails,

            eventMatch[0].strHomeLineupGoalkeeper,
            eventMatch[0].strHomeLineupDefense,
            eventMatch[0].strHomeLineupMidfield,
            eventMatch[0].strHomeLineupForward,
            eventMatch[0].strHomeLineupSubstitutes,
            // away
            eventMatch[0].idAwayTeam,
            eventMatch[0].strAwayTeam,
            eventMatch[0].intAwayScore,
            eventMatch[0].strAwayGoalDetails,

            eventMatch[0].strAwayLineupGoalkeeper,
            eventMatch[0].strAwayLineupDefense,
            eventMatch[0].strAwayLineupMidfield,
            eventMatch[0].strAwayLineupForward,
            eventMatch[0].strAwayLineupSubstitutes
        )
    }

    private fun setFavorite() {
        if (isFavourite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_fav, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }R.id.add_to_favorite-> {
                if (isFavourite) removeFromFavourite() else addToFavourite()
                isFavourite = !isFavourite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addToFavourite(){
        try {
            database.use {
                insert(MatchFavorite.TABLE_MATCH,
                    MatchFavorite.EVENT_ID to matchf.idEvent,
                    MatchFavorite.EVENT_NAME to matchf.strEvent,
                    MatchFavorite.EVENT_DATE to datef,
                    MatchFavorite.HOME_ID to matchf.idHomeTeam,
                    MatchFavorite.HOME_TEAM to matchf.strHomeTeam,
                    MatchFavorite.HOME_SCORE to matchf.intHomeScore,
                    MatchFavorite.AWAY_ID to matchf.idAwayTeam,
                    MatchFavorite.AWAY_TEAM to matchf.strAwayTeam,
                    MatchFavorite.AWAY_SCORE to matchf.intAwayScore
                )
            }
            content.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            content.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavourite(){
        try {
            database.use {
                delete(MatchFavorite.TABLE_MATCH, "(EVENT_ID = {id})",
                    "id" to idEvent)
            }
            content.snackbar( "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            content.snackbar(e.localizedMessage).show()
        }
    }
}
