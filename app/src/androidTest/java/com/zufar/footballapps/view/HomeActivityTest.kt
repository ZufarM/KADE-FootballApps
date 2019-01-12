package com.zufar.footballapps.view

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.zufar.footballapps.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest{

    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    /* Instrumentation Testing
    * Menguji setiap halaman navigasi pada HomeActivity
    * Terdapat 3 navigasi utama
    *
    * - Match
    *   Menampilkan List Jadwal Pertandingan yang akan datang dan yang sudah berlangsung berdasarkan liga
    *   dan kita dapat mencari pertandingan yang ingin kita lihat dalam fitur pencarian
    *
    * - Team
    *   Menampilkan List Team Sepakbola berdasarkan liga
    *
    * - Favorite
    *   Menampilkan List Jadwal Sepakbola yang dipilih sebagai favorite dan
    *   Menampilkan List Team Sepakbola yang dipilih sebagai favorite
    *
    */

    @Test
    fun testNavMatchBehaviour() {
        Thread.sleep(3000)
        onView(withId(navigation_matches))
            .check(matches(isDisplayed()))
        onView(withId(navigation_matches)).perform(click())

        Thread.sleep(3000)
        onView(withText("NEXT MATCH"))
            .check(matches(isDisplayed()))
        onView(withText("NEXT MATCH")).perform(click())
        Thread.sleep(3000)
        onView(withId(rv_next_list_match))
            .check(matches(isDisplayed()))
        onView(withId(rv_next_list_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rv_next_list_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))

        Thread.sleep(5000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
            .check(matches(isDisplayed()))
        Thread.sleep(5000)

        pressBack()

        Thread.sleep(3000)
        onView(withText("PAST MATCH"))
            .check(matches(isDisplayed()))
        onView(withText("PAST MATCH")).perform(click())
        Thread.sleep(3000)
        onView(withId(rv_past_list_match))
            .check(matches(isDisplayed()))
        onView(withId(rv_past_list_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rv_past_list_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))

        Thread.sleep(5000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
            .check(matches(isDisplayed()))
        Thread.sleep(5000)

        pressBack()

        Thread.sleep(3000)
        onView(withId(search))
            .check(matches(isDisplayed()))
        onView(withId(search)).perform(click())
        Thread.sleep(3000)
        onView(withId(rv_search_match))
            .check(matches(isDisplayed()))
        onView(withId(rv_search_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rv_search_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))

        Thread.sleep(5000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
            .check(matches(isDisplayed()))
        Thread.sleep(5000)
    }

    @Test
    fun testNavTeamBehaviour() {
        Thread.sleep(3000)
        onView(withId(navigation_teams))
            .check(matches(isDisplayed()))
        onView(withId(navigation_teams)).perform(click())

        Thread.sleep(3000)
        onView(withId(rv_list_team))
            .check(matches(isDisplayed()))
        onView(withId(rv_list_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rv_list_team)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))

        Thread.sleep(5000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
            .check(matches(isDisplayed()))
        Thread.sleep(5000)

        pressBack()

        Thread.sleep(3000)
        onView(withId(search))
            .check(matches(isDisplayed()))
        onView(withId(search)).perform(click())

        Thread.sleep(3000)
        onView(withId(rv_search_team))
            .check(matches(isDisplayed()))
        onView(withId(rv_search_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rv_search_team)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))

        Thread.sleep(5000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
            .check(matches(isDisplayed()))
        Thread.sleep(5000)
    }

    @Test
    fun testNavFavoriteBehaviour() {
        Thread.sleep(3000)
        onView(withId(navigation_favorites))
            .check(matches(isDisplayed()))
        onView(withId(navigation_favorites)).perform(click())

        Thread.sleep(3000)
        onView(withText("TEAM"))
            .check(matches(isDisplayed()))
        onView(withText("TEAM")).perform(click())

        Thread.sleep(3000)
        onView(withId(rv_fav_team))
            .check(matches(isDisplayed()))
        onView(withId(rv_fav_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(rv_fav_team)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Thread.sleep(3000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed to favorite"))
            .check(matches(isDisplayed()))
        Thread.sleep(5000)

        pressBack()

        Thread.sleep(3000)
        onView(withText("MATCH"))
            .check(matches(isDisplayed()))
        onView(withText("MATCH")).perform(click())

        Thread.sleep(3000)
        onView(withId(rv_fav_match))
            .check(matches(isDisplayed()))
        onView(withId(rv_fav_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(rv_fav_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Thread.sleep(3000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed to favorite"))
            .check(matches(isDisplayed()))
        Thread.sleep(5000)

        pressBack()
    }
}