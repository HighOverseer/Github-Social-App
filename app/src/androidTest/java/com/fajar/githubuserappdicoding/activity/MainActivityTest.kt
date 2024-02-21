package com.fajar.githubuserappdicoding.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test
import com.fajar.githubuserappdicoding.R

class MainActivityTest {

/*    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun shouldNotReturnHomeWhenBackPressWithSearchViewDisplayed() {
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.search_bar)).perform(click())
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        pressBack()
        pressBack()
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()))
    }*/
}