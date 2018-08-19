package com.brainasaservice.api.review.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Review(
    @SerializedName("review_id") val id: Long,
    @SerializedName("rating") val rating: Double,
    @SerializedName("title") val title: String?,
    @SerializedName("message") val message: String,
    @SerializedName("author") val author: String,
    @SerializedName("foreignLanguage") val foreignLanguage: Boolean,
    @SerializedName("date") val date: Date,
    @SerializedName("languageCode") val languageCode: String,
    @SerializedName("traveler_type") val travelerType: String,
    @SerializedName("reviewerName") val reviewerName: String,
    @SerializedName("reviewerCountry") val reviewerCountry: String
)
