package com.brainasaservice.api.review

enum class ReviewSortDirection(val value: String) {
    DESC("desc"),
    ASC("asc");

    companion object {
        fun fromValue(value: String) = when (value) {
            "desc" -> DESC
            else -> ASC
        }
    }
}