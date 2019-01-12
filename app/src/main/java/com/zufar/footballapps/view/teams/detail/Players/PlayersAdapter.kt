package com.zufar.footballapps.view.teams.detail.Players

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.zufar.footballapps.R
import com.zufar.footballapps.model.Player
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class PlayersAdapter (private val player: List<Player>,
                      private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(player[position], listener)
    }

    override fun getItemCount(): Int = player.size

}

class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.player_profile
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.player_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }
            }
        }
    }
}

class PlayerViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    private val playerProfile: ImageView = view.find(R.id.player_profile)
    private val playerName: TextView = view.find(R.id.player_name)

    fun bindItem(player: Player, listener: (Player) -> Unit) {
        Picasso.get().load(player.strCutout).into(playerProfile)
        playerName.text = player.strPlayer
        view.onClick { listener(player) }
    }
}