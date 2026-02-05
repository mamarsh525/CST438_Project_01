package com.example.project01group03.API

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DiscogsApiService {
    @GET("database/search")
    suspend fun searchArtists(
       //discogs site says
        // Your application must provide a User-Agent string that identifies itself – preferably something that follows RFC 1945.
        // it requires a unique user agent to identify our app,not sure if we need it but Ill add anyways
        @Header("User-Agent") userAgent: String = "cst438Project01Group03",
        //search query
        @Query("q") query: String,
        //since im doing the random artist, have to set type to artist, so it only shows artits and not other data
        //can make it show the artists albums later for now just doing random artist
        @Query("type") type: String = "artist",

        //token that discogs requires, we should be able to get iirc 60 requests a minute
        @Query("token") token: String = "IrbQNfaSiELvXDWpqyjjmfooRkuPNUGLNtxfAIVi",

        //Used to get a random result by picking a random page number and that shows random artist
        // discogs separates all the artists into pages, so each page is an artist, gonna eventually
        //make a random page generator and thatll get us the random artist for the random activity
        @Query("page") page: Int,

        // We only need 1 result to be shown for the random button
         @Query("per_page") perPage: Int = 1


    ): DiscogsSearchResponse


}
