package com.zufar.footballapps.view.matches.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.gson.Gson
import com.zufar.footballapps.R
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.model.Match
import com.zufar.footballapps.util.invisible
import com.zufar.footballapps.util.visible
import com.zufar.footballapps.view.matches.detail.DetailMatchActivity
import com.zufar.footballapps.view.matches.MatchAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private var match: MutableList<Match> = mutableListOf()
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var listEvent: RecyclerView
    private lateinit var adapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_search_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.rv_search_match
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

        adapter = MatchAdapter(match){
            //Toast.makeText(ctx,"${it.strEvent}", Toast.LENGTH_LONG).show()
            startActivity<DetailMatchActivity>(
                // Event
                "id" to "${it.idEvent}",
                // home Team
                "homeId" to "${it.idHomeTeam}",
                // away Team
                "awayId" to "${it.idAwayTeam}"
            )
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchMatchPresenter(this, request, gson)
        presenter.searchMatchList("man")
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
                presenter.searchMatchList(s)
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

    override fun showMatchSearch(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
