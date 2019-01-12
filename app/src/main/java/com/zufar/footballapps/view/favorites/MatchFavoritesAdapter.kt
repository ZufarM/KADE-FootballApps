package com.zufar.footballapps.view.favorites

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.zufar.footballapps.R
import com.zufar.footballapps.db.MatchFavorite
import org.jetbrains.anko.*

class MatchFavoritesAdapter(private  val favorite: List<MatchFavorite>, private val listener: (MatchFavorite) -> Unit) :
    RecyclerView.Adapter<MatchFavViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchFavViewHolder {
        return MatchFavViewHolder(FavMatchUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun onBindViewHolder(holder: MatchFavViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size
}

class FavMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                padding = dip(4)
                gravity = Gravity.CENTER

                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)
                    gravity = Gravity.CENTER_VERTICAL
                    backgroundColor = android.R.color.white

                    textView {
                        id = R.id.date_event_fav
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                    }.lparams(width = matchParent, height = wrapContent)


                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        lparams(width = matchParent, height = wrapContent)
                        gravity = Gravity.CENTER
                        // Home
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                gravity = Gravity.RIGHT
                                id = R.id.home_team_fav
                                textSize = 19f
                                text = " Home "
                            }
                        }.lparams(width = 300, height = wrapContent)
                        // Score Home
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            textView {
                                id = R.id.home_score_fav
                                gravity = Gravity.CENTER
                                textSize = 20f
                                leftPadding = dip(16)
                                text = " 0 "
                                setTypeface(null, Typeface.BOLD)
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = " vs "
                                setTypeface(null, Typeface.ITALIC)
                            }
                            // Score Away
                            textView {
                                id = R.id.away_score_fav
                                gravity = Gravity.CENTER
                                textSize = 20f
                                rightPadding = dip(16)
                                text = " 0 "
                                setTypeface(null, Typeface.BOLD)
                            }
                        }
                        // Away
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            textView {
                                id = R.id.away_team_fav
                                gravity = Gravity.LEFT
                                text = " Away "
                                textSize = 19f
                            }
                        }.lparams(width = 300, height = wrapContent)
                    }
                }
            }
        }
    }
}

class MatchFavViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val dateEvent: TextView = view.find(R.id.date_event_fav)
    private val strHomeTeam: TextView = view.find(R.id.home_team_fav)
    private val intHomeScore: TextView = view.find(R.id.home_score_fav)
    private val strAwayTeam: TextView = view.find(R.id.away_team_fav)
    private val intAwayScore: TextView = view.find(R.id.away_score_fav)

    fun bindItem(favorite: MatchFavorite, listener: (MatchFavorite) -> Unit){
        // Date
        dateEvent.text = favorite.eventDate
        // Home
        strHomeTeam.text = favorite.homeTeam
        intHomeScore.text = favorite.homeScore
        // Away
        strAwayTeam.text = favorite.awayTeam
        intAwayScore.text = favorite.awayScore

        view.setOnClickListener { listener(favorite) }
    }
}