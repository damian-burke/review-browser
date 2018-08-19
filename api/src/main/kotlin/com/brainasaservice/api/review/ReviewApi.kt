package com.brainasaservice.api.review

import com.brainasaservice.api.review.response.ReviewResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewApi {

    @GET(GET_REVIEW_URI)
    fun getReviews(
            @Path(VAR_LOCATION) location: String,
            @Path(VAR_TOUR) tour: String,
            @Query(VAR_COUNT) count: Int,
            @Query(VAR_PAGE) page: Int,
            @Query(VAR_RATING) rating: Int,
            @Query(VAR_SORTBY) sortBy: String,
            @Query(VAR_DIRECTION) direction: String
    ): Single<ReviewResponse>

    private companion object {
        const val VAR_LOCATION = "location"
        const val VAR_TOUR = "tour"
        const val VAR_COUNT = "count"
        const val VAR_PAGE = "page"
        const val VAR_RATING = "rating"
        const val VAR_SORTBY = "sortBy"
        const val VAR_DIRECTION = "direction"
        const val GET_REVIEW_URI = "{$VAR_LOCATION}/{$VAR_TOUR}/reviews.json"
    }
}