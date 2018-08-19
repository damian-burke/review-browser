package com.brainasaservice.api.review

import org.junit.Test
import kotlin.test.assertTrue

class ReviewSortDirectionTest {

    @Test
    fun `sort direction asc returns ASC enum`() {
        // given
        val key = "asc"

        // when
        val enum = ReviewSortDirection.fromValue(key)

        // then
        assertTrue { enum == ReviewSortDirection.ASC }
    }

    @Test
    fun `sort direction desc returns DESC enum`() {
        // given
        val key = "desc"

        // when
        val enum = ReviewSortDirection.fromValue(key)

        // then
        assertTrue { enum == ReviewSortDirection.DESC }
    }
}
