package com.indie.whitstan.dohanyradar.network

import com.indie.whitstan.dohanyradar.interfaces.TobaccoShopAPI
import com.indie.whitstan.dohanyradar.model.TobaccoShop
import com.indie.whitstan.dohanyradar.model.TobaccoShopDetails
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient {
    fun createTobaccoShopListCall(): Call<List<TobaccoShop>?>? {
        return createTobaccoShopAPI().getTobaccoShopsList
    }

    fun createTobaccoShopListCallById(id: Int): Call<TobaccoShopDetails>? {
        return createTobaccoShopAPI().getTobaccoShopDetails(id)
    }

    fun createTobaccoShopListCallByQueryString(queryString: String): Call<List<TobaccoShop>?>? {
        return createTobaccoShopAPI().getTobaccoShopsListByQueryString(queryString)
    }

    private fun createTobaccoShopAPI() : TobaccoShopAPI{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit.create(TobaccoShopAPI::class.java)
    }

    companion object {
        const val BASE_URL = "https://dohanyradar.codevisionkft.hu/"
    }
}