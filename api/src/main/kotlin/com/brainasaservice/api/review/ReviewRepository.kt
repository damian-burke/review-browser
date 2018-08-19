package com.brainasaservice.api.review

import com.brainasaservice.api.review.response.Review
import io.reactivex.Single

/**
 *
 */
interface ReviewRepository {
    /**
     * Fetches a list of reviews for a given location and tour with set constraints.
     *
     * @param location
     * @param tour
     * @param count
     * @param page
     * @param rating
     * @param sortBy
     * @param direction
     */
    fun fetchReviews(
            location: String,
            tour: String,
            count: Int,
            page: Int = 0,
            rating: Int = 0,
            sortBy: ReviewSortBy = ReviewSortBy.DATE,
            direction: ReviewSortDirection = ReviewSortDirection.DESC
    ): Single<List<Review>>
}
