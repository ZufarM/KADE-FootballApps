package com.zufar.footballapps.view.teams.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.Gson
import com.zufar.footballapps.R
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.model.Team
import com.zufar.footballapps.util.invisible
import com.zufar.footballapps.util.visible
import com.zufar.footballapps.view.teams.TeamPresenter
import com.zufar.footballapps.view.teams.TeamView
import com.zufar.footballapps.view.teams.TeamAdapter
import com.zufar.footballapps.view.teams.detail.DetailTeamActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class SearchTeamActivity : AppCompatActivity(), SearchTeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: SearchTeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_search_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.rv_search_team
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }

        adapter = TeamAdapter(teams) {
            Toast.makeText(ctx,"${it.strTeam}", Toast.LENGTH_LONG).show()
            startActivity<DetailTeamActivity>("id" to "${it.idTeam}")
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchTeamPresenter(this, request, gson)
        presenter.searchTeamList("man")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)

        val searchItem = menu.findItem(R.id.search_mview)
        val searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search Match")
        searchItem.expandActionView()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextChange(s: String): Boolean {
                //Toast.makeText(this@SearchMatchActivity, s,Toast.LENGTH_LONG).show()
//                presenter.searchMatchList(s)
                return false
            }

            override fun onQueryTextSubmit(s: String): Boolean {
                //Toast.makeText(this@SearchMatchActivity, s, Toast.LENGTH_LONG).show()
                presenter.searchTeamList(s)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.search_mview -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamSearch(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
