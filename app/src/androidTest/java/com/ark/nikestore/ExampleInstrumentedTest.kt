package com.ark.nikestore

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ark.nikestore.databinding.ItemProductBinding
import com.ark.nikestore.feature.common.ProductListAdapter
import com.ark.nikestore.feature.home.HomeFragment
import com.ark.nikestore.feature.main.MainActivity
import org.hamcrest.Matcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ark.nikestore", appContext.packageName)
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun scroll_to_last_product_item() {

        /*onView(withId(R.id.latestProductsRv))
            .perform(RecyclerViewActions
                .scrollTo<ProductListAdapter.ViewHolder<ItemProductBinding>>(
                    ViewMatchers.hasDescendant(withSubstring("test"))
                ))*/

        onView(isRoot()).perform(waitFor(2000)) //because might the progressBar shows on the views

        onView(withId(R.id.latestProductsRv))
            .perform(RecyclerViewActions
                .scrollToLastPosition<ProductListAdapter.ViewHolder<ItemProductBinding>>())

        onView(withSubstring("Jordan")).perform(click())

        onView(withSubstring("مناسب هست")).check(matches(isDisplayed()))
    }

    @Test
    fun navigate_from_homeFragment_to_product_detail_activity(){
        //Navigation testing
        //No need to launch activity before it

        val homeFragmentScenario =           //to launch fragment
            launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_NikeStore)
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        homeFragmentScenario.onFragment{
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(isRoot()).perform(waitFor(2000)) //because might the progressBar shows on the views

        onView(withId(R.id.latestProductsRv)).perform(RecyclerViewActions.
        actionOnItemAtPosition<ProductListAdapter.ViewHolder<ItemProductBinding>>(
            1, click()
        ))

        //assertEquals(R.id.home, navController.currentDestination?.id)

        onView(withSubstring("برای دویدن")).check(matches(isDisplayed()))
    }

    private fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}