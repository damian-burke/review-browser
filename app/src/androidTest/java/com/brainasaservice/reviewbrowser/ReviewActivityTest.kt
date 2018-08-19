package com.brainasaservice.reviewbrowser

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.azimolabs.conditionwatcher.ConditionWatcher
import com.brainasaservice.api.review.ReviewRepositoryImpl
import com.brainasaservice.api.review.response.Review
import com.brainasaservice.reviewbrowser.ui.review.ReviewActivity
import com.brainasaservice.reviewbrowser.util.WaitInstruction
import com.brainasaservice.reviewbrowser.util.withTopDrawable
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_review.recyclerView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class ReviewActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<ReviewActivity>(ReviewActivity::class.java, true, false)

    @Test
    fun initialViewStateWithoutData() {
        // given
        ReviewRepositoryImpl.REVIEW_RESPONSE = Single.just(emptyList())

        // when
        activityTestRule.launchActivity(Intent())

        // then
        ConditionWatcher.waitForCondition(WaitInstruction())
        assert(activityTestRule.activity.recyclerView.adapter.itemCount == 0)
    }

    @Test
    fun initialViewStateWithListData() {
        // given
        ReviewRepositoryImpl.REVIEW_RESPONSE = Single.just(reviewList)

        // when
        activityTestRule.launchActivity(Intent())

        // then
        ConditionWatcher.waitForCondition(WaitInstruction())
        assert(activityTestRule.activity.recyclerView.adapter.itemCount == reviewList.size)
    }

    @Test
    fun initialSortByIsDate() {
        // given
        ReviewRepositoryImpl.REVIEW_RESPONSE = Single.just(reviewList)

        // when
        activityTestRule.launchActivity(Intent())
        ConditionWatcher.waitForCondition(WaitInstruction())

        // then
        onView(withId(R.id.textSortBy)).check(matches(withTopDrawable(R.drawable.ic_date_range)))
    }

    @Test
    fun togglingSortByToRating() {
        // given
        ReviewRepositoryImpl.REVIEW_RESPONSE = Single.just(reviewList)

        // when
        activityTestRule.launchActivity(Intent())
        ConditionWatcher.waitForCondition(WaitInstruction())

        onView(withId(R.id.textSortBy)).perform(click())
        ConditionWatcher.waitForCondition(WaitInstruction())

        // then
        onView(withId(R.id.textSortBy)).check(matches(withTopDrawable(R.drawable.ic_rate_review)))
    }

    private companion object {
        val reviewList = listOf<Review>(
                Review(1, 1.0, "Mock Review #1", "This is a mocked review. It is not very positive.", "Mr. Mock", true, Date(), "de", "traveler", "Mockito", "Germany"),
                Review(2, 3.0, "Mock Review #2", "This is a mocked review. It is not very decisive, it's just a 3/5. It's mostly positive, but not really that good.", "Mr. Mockler", true, Date(), "de", "traveler", "Mockler", "France")
        )
    }
}
