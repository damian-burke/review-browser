package com.brainasaservice.api.review

import com.brainasaservice.api.review.response.Review
import com.brainasaservice.api.review.response.ReviewResponse
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.Date

class ReviewRepositoryTest {

    @Mock
    lateinit var api: ReviewApi

    lateinit var repository: ReviewRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = ReviewRepositoryImpl(api)
    }

    @Test
    fun `status true with data returns list of reviews`() {
        // given
        val review1Date = Date()
        val review1 = Review(1, 1.0, "title", "message", "author", true, review1Date, "de", "type", "name", "country")

        val review2Date = Date()
        val review2 = Review(2, 2.0, "title-2", "message-2", "author-2", false, review2Date, "en", "type-2", "name-2", "country-2")

        val data = listOf<Review>(
                review1, review2
        )
        val response = ReviewResponse(
                status = true,
                totalReviewComments = 0,
                data = data,
                message = null
        )

        whenever(api.getReviews(any(), any(), any(), any(), any(), any(), any())).thenReturn(Single.just(response))

        // when
        val test = repository.fetchReviews("location", "tour", 10)
                .test()

        // then
        test
                .assertNoErrors()
                .assertValue { list ->
                    list == data
                }
                .assertComplete()
    }

    @Test
    fun `status true with null data returns empty list`() {
        // given
        val response = ReviewResponse(
                status = true,
                totalReviewComments = 0,
                data = null,
                message = null
        )
        whenever(api.getReviews(any(), any(), any(), any(), any(), any(), any())).thenReturn(Single.just(response))

        // when
        val test = repository.fetchReviews("location", "tour", 10)
                .test()

        // then
        test
                .assertNoErrors()
                .assertValue { list -> list.isEmpty() }
                .assertComplete()
    }

    @Test
    fun `status false returns empty list`() {
        // given
        val response = ReviewResponse(
                status = false,
                totalReviewComments = 0,
                data = null,
                message = null
        )
        whenever(api.getReviews(any(), any(), any(), any(), any(), any(), any())).thenReturn(Single.just(response))

        // when
        val test = repository.fetchReviews("location", "tour", 10)
                .test()

        // then
        test
                .assertNoErrors()
                .assertValue { list -> list.isEmpty() }
                .assertComplete()
    }
}