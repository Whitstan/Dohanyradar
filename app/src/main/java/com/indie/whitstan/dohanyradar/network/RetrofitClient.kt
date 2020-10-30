package com.indie.whitstan.dohanyradar.network

import com.indie.whitstan.dohanyradar.interfaces.TobaccoShopAPI
import com.indie.whitstan.dohanyradar.model.TobaccoShop
import com.indie.whitstan.dohanyradar.model.TobaccoShopDetails
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient {
    fun createTobaccoShopListCall(): Call<List<TobaccoShop>?>? {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        val tobaccoApi = retrofit.create(TobaccoShopAPI::class.java)
        return tobaccoApi.getTobaccoShopsList
    }

    fun createTobaccoShopListCallById(id: Int): Call<TobaccoShopDetails>? {
        
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        val tobaccoApi = retrofit.create(TobaccoShopAPI::class.java)
        return tobaccoApi.getTobaccoShopDetails(id)
    }

    companion object {
        const val BASE_URL = "https://dohanyradar.codevisionkft.hu/"
    }
}