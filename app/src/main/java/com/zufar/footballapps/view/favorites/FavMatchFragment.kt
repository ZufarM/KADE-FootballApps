package com.zufar.footballapps.view.favorites


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.zufar.footballapps.R
import com.zufar.footballapps.db.MatchFavorite
import com.zufar.footballapps.db.database
import com.zufar.footballapps.view.matches.detail.DetailMatchActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavMatchFragment : Fragment(), AnkoComponent<Context> {

    private var favourites: MutableList<MatchFavorite> = mutableListOf()
    private lateinit var adapter: MatchFavoritesAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MatchFavoritesAdapter(favourites){
            context?.startActivity<DetailMatchActivity>(
                // Event
                "id" to "${it.eventId}",
                // home Team
                "homeId" to "${it.homeId}",
                // away Team
                "awayId" to "${it.awayId}"
            )
        }
        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favourites.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(MatchFavorite.TABLE_MATCH)
            val favourite = result.parseList(classParser<MatchFavorite>())
            favourites.addAll(favourite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.rv_fav_match
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }
}
