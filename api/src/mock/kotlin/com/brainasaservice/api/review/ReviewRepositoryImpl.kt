package com.brainasaservice.api.review

import com.brainasaservice.api.review.response.Review
import io.reactivex.Single
import java.util.Collections.emptyList
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor() : ReviewRepository {
    override fun fetchReviews(
            location: String,
            tour: String,
            count: Int,
            page: Int,
            rating: Int,
            sortBy: ReviewSortBy,
            direction: ReviewSortDirection
    ): Single<List<Review>> {
        return REVIEW_RESPONSE
    }

    companion object {
        var REVIEW_RESPONSE: Single<List<Review>> = Single.just(emptyList<Review>())
    }
}
