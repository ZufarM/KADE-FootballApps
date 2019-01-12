package com.zufar.footballapps.view.teams.detail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
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
import com.zufar.footballapps.R.menu.detail_fav
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.db.TeamFavorite
import com.zufar.footballapps.db.database
import com.zufar.footballapps.model.Team
import com.zufar.footballapps.view.teams.detail.Overview.OverviewFragment
import com.zufar.footballapps.view.teams.detail.Players.PlayersFragment
//import com.zufar.footballapps.view.teams.detail.Players.PlayersFragment
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {

    private lateinit var presenter: DetailTeamPresenter
    private var team: MutableList<Team> = mutableListOf()

    private lateinit var mSectionsPagerAdapter: DetailTeamPagerAdapter
    private lateinit var idTeam: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var teamf: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idTeam = intent.getStringExtra("id")

        mSectionsPagerAdapter = DetailTeamPagerAdapter(this.supportFragmentManager)

        pagerTeam.adapter = mSectionsPagerAdapter
        pagerTeam.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(pagerTeam))

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailTeamPresenter(this, request, gson)
        presenter.getDetailTeam(idTeam)
    }

    private fun favoriteState(){
        database.use {
            val result = select(TeamFavorite.TABLE_TEAM)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to idTeam)
            val favorite = result.parseList(classParser<TeamFavorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showTeamDetail(data: List<Team>) {
        team.clear()
        team.addAll(data)

        val teamImg = findViewById<ImageView>(R.id.img_team)
        val teamName = findViewById<TextView>(R.id.tv_team_name)
        val teamYear = findViewById<TextView>(R.id.tv_team_year)
        val teamStadium = findViewById<TextView>(R.id.tv_team_stadium)
        val teamOverview = findViewById<TextView>(R.id.tv_team_overview)

        Picasso.get()
            .load(data[0].strTeamBadge)
            .into(teamImg)

        teamName.text = data[0].strTeam
        teamYear.text = data[0].intFormedYear
        teamStadium.text = data[0].strStadium
        teamOverview.text = data[0].strDescriptionEN

        teamf = Team(
            data[0].idTeam,
            data[0].strTeam,
            data[0].strTeamBadge,
            data[0].intFormedYear,
            data[0].strStadium,
            data[0].strDescriptionEN
        )
    }

    inner class DetailTeamPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            when (position) {
                0 -> return OverviewFragment()
                1 -> return PlayersFragment.newInstance(idTeam)
            }
            //return PlaceholderFragment.newInstance(position + 1)
            return OverviewFragment()
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_fav, menu)
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
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(TeamFavorite.TABLE_TEAM,
                    TeamFavorite.TEAM_ID to teamf.idTeam,
                    TeamFavorite.TEAM_NAME to teamf.strTeam,
                    TeamFavorite.TEAM_ICON to teamf.strTeamBadge
                )
            }
            detail_team.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            detail_team.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(TeamFavorite.TABLE_TEAM, "(TEAM_ID = {id})",
                    "id" to idTeam)
            }
            detail_team.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            detail_team.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}
