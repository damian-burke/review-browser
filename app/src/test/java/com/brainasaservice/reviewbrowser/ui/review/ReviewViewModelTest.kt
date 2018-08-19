package com.brainasaservice.reviewbrowser.ui.review

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.brainasaservice.api.review.ReviewRepository
import com.brainasaservice.api.review.response.Review
import com.brainasaservice.reviewbrowser.TestScheduler
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.Date
import kotlin.test.assertTrue

class ReviewViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: ReviewRepository

    lateinit var viewModel: ReviewViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        // mock first page for initialization call
        whenever(repository.fetchReviews(any(), any(), any(), any(), any(), any(), any())).thenReturn(Single.just(emptyList()))

        viewModel = ReviewViewModel(repository, TestScheduler)
    }

    @Test
    fun `initialization fetches page 0 of review dataset`() {
        // given
        val page = 0

        // when

        // then
        verify(repository).fetchReviews(any(), any(), any(), eq(page), any(), any(), any())
    }

    @Test
    fun `setting item count value sets count livedata value`() {
        // given
        val count = 10

        // when
        viewModel.setCount(count)

        // then
        assertTrue { viewModel.observeCountSetting().value == count }
    }

    @Test
    fun `setting item count value refreshes data`() {
        // given
        val count = 10

        // when
        viewModel.setCount(count)

        // then
        verify(repository, times(2)).fetchReviews(any(), any(), any(), eq(0), any(), any(), any())
    }

    @Test
    fun `setting item count value updates livedata with fetched data`() {
        // given
        val count = 10
        val review = Review(1, 1.0, "title", "message", "author", true, Date(), "en", "type", "name", "country")
        val reviews = listOf<Review>(review)

        // when
        whenever(repository.fetchReviews(any(), any(), any(), any(), any(), any(), any())).thenReturn(Single.just(reviews))
        viewModel.setCount(count)

        // then
        assertTrue { viewModel.observeReviews().value == reviews }
    }
}
