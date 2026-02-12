package com.example.project01group03.API
//library that translates JSON into kotlin objects for the app since its kotlin based
import com.google.gson.annotations.SerializedName

//https://www.discogs.com/developers/#page:database,header:database-artist
//webpage to documentation and how to use their database
data class DiscogsSearchResponse(
    @SerializedName("results") val results: List<SearchResult>
)

//parameters used on discogs documentation,
//type for artist, title and id for artist
data class SearchResult(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("thumb") val thumb: String, // URL for the thumbnail image
    @SerializedName("year") val year: String?,
    val type: String,
    @SerializedName("cover_image") val coverImage: String,
    @SerializedName("resource_url") val resourceUrl: String
)
