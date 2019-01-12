package com.zufar.footballapps.view.teams.detail.Players.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.zufar.footballapps.R
import com.zufar.footballapps.api.ApiRepository
import com.zufar.footballapps.model.Player
import com.zufar.footballapps.view.teams.detail.Players.PlayersAdapter
import com.zufar.footballapps.view.teams.detail.Players.PlayersPresenter
import com.zufar.footballapps.view.teams.detail.Players.PlayersView

class DetailPlayerActivity : AppCompatActivity(), PlayersView {

    private lateinit var presenter: PlayersPresenter
    private var player: MutableList<Player> = mutableListOf()

    private lateinit var idPlayer: String
    private lateinit var namePlayer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idPlayer = intent.getStringExtra("idPlayer")
        namePlayer = intent.getStringExtra("namePlayer")

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayersPresenter(this,request,gson)
        presenter.getDetailPlayer(idPlayer)

        val actionBar = supportActionBar
        actionBar?.title = namePlayer
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showPlayerList(data: List<Player>) {
        player.clear()
        player.addAll(data)

        val playerImg = findViewById<ImageView>(R.id.img_player)
        val playerHeight = findViewById<TextView>(R.id.tv_player_height)
        val playerWeight = findViewById<TextView>(R.id.tv_player_weight)
        val playerDesc = findViewById<TextView>(R.id.tv_player_desc)

        Picasso.get()
            .load(data[0].strFanart1)
            .into(playerImg)

        playerHeight.text = data[0].strHeight
        playerWeight.text = data[0].strWeight
        playerDesc.text = data[0].strDescriptionEN
    }
}
