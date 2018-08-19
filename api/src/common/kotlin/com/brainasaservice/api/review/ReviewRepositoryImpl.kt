package com.brainasaservice.api.review

import com.brainasaservice.api.review.response.Review
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepositoryImpl @Inject constructor(
        private val reviewApi: ReviewApi
) : ReviewRepository {

    override fun fetchReviews(
            location: String,
            tour: String,
            count: Int,
            page: Int,
            rating: Int,
            sortBy: ReviewSortBy,
            direction: SortDirection
    ): Single<List<Review>> {
        return reviewApi.getReviews(
                location,
                tour,
                count,
                page,
                rating,
                sortBy.value,
                direction.value
        ).map {
            if (it.status && it.data != null) {
                it.data
            } else {
                emptyList()
            }
        }
    }
}
