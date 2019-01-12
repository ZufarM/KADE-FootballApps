package com.zufar.footballapps.view.favorites


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.zufar.footballapps.R
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private lateinit var favPagerAdapter: FavPagerAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favPagerAdapter = FavPagerAdapter(this.childFragmentManager)

        match_container_fav.adapter = favPagerAdapter
        match_container_fav.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs_fav))
        tabs_fav.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(match_container_fav))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        return view
    }

    inner class FavPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            when (position) {
                0 -> return FavMatchFragment()
                1 -> return FavTeamFragment()
            }
            //return PlaceholderFragment.newInstance(position + 1)
            return FavTeamFragment()
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    }
}
