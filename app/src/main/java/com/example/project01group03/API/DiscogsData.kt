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
    val id: Int,//id of artist
    val title: String,//name of artist
    val type: String,// when you search for a name, multiple different results can come up
    //discogs uses type to filter for album, artist, label etc
    @SerializedName("cover_image") val coverImage: String,
    @SerializedName("resource_url") val resourceUrl: String
)
