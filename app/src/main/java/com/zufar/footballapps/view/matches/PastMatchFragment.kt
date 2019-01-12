package com.zufar.footballapps.view.matches

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.zufar.footballapps.R
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.model.Match
import com.zufar.footballapps.util.gone
import com.zufar.footballapps.util.visible
import com.zufar.footballapps.view.matches.detail.DetailMatchActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PastMatchFragment : Fragment(), AnkoComponent<Context>, MatchView {

    private var match: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var spinner: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = MatchAdapter(match){
            Toast.makeText(ctx,"${it.strEvent}", Toast.LENGTH_LONG).show()
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

        val args = arguments
        val event = "past"//args?.getString("event")

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, request, gson)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getMatchList(event, leagueName)
                // https://www.thesportsdb.com/api/v1/json/1/all_leagues.php
                when(leagueName){
                    "English Premier League" -> presenter.getMatchList(event,"4328")
                    "English League Championship" -> presenter.getMatchList(event,"4329")
                    "German Bundesliga" -> presenter.getMatchList(event,"4331")
                    "Italian Serie A" -> presenter.getMatchList(event,"4332")
                    "French Ligue 1" -> presenter.getMatchList(event,"4334")
                    "Spanish La Liga" -> presenter.getMatchList(event,"4335")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        swipeRefresh.onRefresh {
            presenter.getMatchList(event,leagueName)
        }
    }

    companion object {
        fun newInstance(event: String): PastMatchFragment {
            val args = Bundle()
            args.putString("event", event)
            val fragment = PastMatchFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner
            }
            swipeRefresh = swipeRefreshLayout {
                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.rv_past_list_match
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
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.gone()
    }

    override fun showMatchList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
