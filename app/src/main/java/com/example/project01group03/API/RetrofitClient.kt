package com.example.project01group03.API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //base url of the api
    //this is basically whats going on behind the scenes
    //https://www.discogs.com/developers/#page:database,header:database-artist
    //take a look at the github sent in chat, used as template for how to get this going
    val baseUrl = "https://api.discogs.com/"
    //retrofit builder that used base url & JSON converter (GSON) for when making a call to api
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
//lazy means it wont be initialized until the very first time its used
    //this is actual implementation of DiscogsApiService
val discogsApiService: DiscogsApiService by lazy {
    retrofit.create(DiscogsApiService::class.java)
}



}





