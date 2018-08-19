package com.brainasaservice.api.review

import kotlin.test.Test
import kotlin.test.assertTrue

class ReviewSortByTest {

    @Test
    fun `sort by rating returns RATING enum`() {
        // given
        val sortByValue = "rating"

        // when
        val enum = ReviewSortBy.fromValue(sortByValue)

        // then
        assertTrue { enum == ReviewSortBy.RATING }
    }

    @Test
    fun `sort by date returns DATE enum`() {
        // given
        val sortByValue = "date_of_review"

        // when
        val enum = ReviewSortBy.fromValue(sortByValue)

        // then
        assertTrue { enum == ReviewSortBy.DATE }
    }
}
