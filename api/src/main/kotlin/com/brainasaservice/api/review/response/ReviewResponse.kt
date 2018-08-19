package com.brainasaservice.api.review.response

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
        @SerializedName("status") val status: Boolean,
        @SerializedName("total_reviews_comments") val totalReviewComments: Int,
        @SerializedName("data") val data: List<Review>?,
        @SerializedName("message") val message: String?
)
