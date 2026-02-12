package com.example.project01group03.API

import com.google.gson.annotations.SerializedName

/**
 * Represents the top-level response from the Discogs API search endpoint.
 */
data class DiscogsSearchResponse(
    @SerializedName("results") val results: List<SearchResult>
)

/**
 * Represents a single search result from the Discogs API.
 * This model is designed to be robust against missing data from the API.
 */
data class SearchResult(
    // The ID can be large, so Long is used to prevent crashes.
    @SerializedName("id") val id: Long,

    @SerializedName("title") val title: String,

    // The year is not always present in the API response, so it must be nullable.
    @SerializedName("year") val year: String?,

    // The thumbnail URL is used for display and is also nullable.
    @SerializedName("thumb") val thumb: String?
)
