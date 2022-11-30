package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ikrima.practice.dicoding.githubuserappdicoding.R
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser.activities.FavoriteActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FavoriteActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(FavoriteActivity::class.java)
    }


    // check when user has list of fav user
    @Test
    fun listFav() {
        onView(withId(R.id.rv_fav_github_users))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.rv_fav_github_users)).perform(click())

    }

    // check when user has not list of fav user
    @Test
    fun notFoundListFav() {
        onView(withId(R.id.empty_layout_fav_user))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}