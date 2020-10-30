package com.indie.whitstan.dohanyradar.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TobaccoShopResponse(
    @field:Json(name = "results") val results: List<TobaccoShop>
)