package com.brainasaservice.api.review

enum class ReviewSortBy(val value: String) {
    DATE("date_of_review"),
    RATING("rating");

    companion object {
        fun fromValue(value: String) = when (value) {
            "date_of_review" -> DATE
            else -> RATING
        }
    }
}