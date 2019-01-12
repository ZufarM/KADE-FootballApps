package com.zufar.footballapps.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zufar.footballapps.R
import com.zufar.footballapps.R.id.*
import com.zufar.footballapps.view.favorites.FavoritesFragment
import com.zufar.footballapps.view.matches.TabMatchesFragment
import com.zufar.footballapps.view.teams.TeamFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                navigation_matches -> {
                    loadMatchesFragment(savedInstanceState)
                }
                navigation_teams -> {
                    loadTeamsFragment(savedInstanceState)
                }
                navigation_favorites -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        navigation.selectedItemId = navigation_matches

    }

    private fun loadMatchesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, TabMatchesFragment(), TabMatchesFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, TeamFragment(), TeamFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FavoritesFragment(), FavoritesFragment::class.java.simpleName)
                .commit()
        }
    }


}
