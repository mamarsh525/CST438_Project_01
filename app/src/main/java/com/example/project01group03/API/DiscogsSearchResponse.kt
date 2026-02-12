package com.example.project01group03.API

import com.google.gson.annotations.SerializedName

// This class models the overall JSON response from the Discogs search endpoint.
data class DiscogsSearchResponse(
    @SerializedName("results") val results: List<SearchResult>
)

// This class models a single item within the search results.
data class SearchResult(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("thumb") val thumb: String, // URL for the thumbnail image
    @SerializedName("year") val year: String?,
)
