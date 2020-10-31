package com.indie.whitstan.dohanyradar.interfaces

import com.indie.whitstan.dohanyradar.model.TobaccoShop
import com.indie.whitstan.dohanyradar.model.TobaccoShopDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TobaccoShopAPI {
    @get:GET("tobbacoshop")
    val getTobaccoShopsList: Call<List<TobaccoShop>?>?

    @GET("tobbacoshop/{id}")
    fun getTobaccoShopDetails(@Path("id") id: Int): Call<TobaccoShopDetails>?

    @GET("tobbacoshop")
    fun getTobaccoShopsListByQueryString(@Query("searchstring") searchstring : String): Call<List<TobaccoShop>?>?
}