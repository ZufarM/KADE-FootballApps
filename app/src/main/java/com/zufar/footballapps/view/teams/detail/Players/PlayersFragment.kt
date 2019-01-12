package com.zufar.footballapps.view.teams.detail.Players


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.zufar.footballapps.R
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.model.Player
import com.zufar.footballapps.util.invisible
import com.zufar.footballapps.util.visible
import com.zufar.footballapps.view.teams.detail.Players.detail.DetailPlayerActivity
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayersFragment : Fragment(), AnkoComponent<Context>, PlayersView {

    private var player: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayersPresenter
    private lateinit var adapter: PlayersAdapter
    private lateinit var listPlayer: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = PlayersAdapter(player){
            Toast.makeText(ctx,"${it.strPlayer}", Toast.LENGTH_LONG).show()
            startActivity<DetailPlayerActivity>(
                "idPlayer" to "${it.idPlayer}",
                "namePlayer" to "${it.strPlayer}"
            )
        }
        listPlayer.adapter = adapter

        val args = arguments
        val idTeam = args?.getString("idTeam")

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayersPresenter(this,request,gson)
        presenter.getPlayerList(idTeam)

        swipeRefresh.onRefresh {
            presenter.getPlayerList(idTeam)
        }
    }

    companion object {
        fun newInstance(idTeam: String): PlayersFragment {
            val args = Bundle()
            args.putString("idTeam", idTeam)
            val fragment = PlayersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout{
            lparams(width = matchParent, height = wrapContent)
            padding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listPlayer = recyclerView {
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
        progressBar.invisible()
    }

    override fun showPlayerList(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
