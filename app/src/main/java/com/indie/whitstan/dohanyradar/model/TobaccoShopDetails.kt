package com.indie.whitstan.dohanyradar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class TobaccoShopDetails(
    @field:Json(name = "id") @PrimaryKey val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "address") val address: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "isOpen") val isOpen : Boolean,
    @field:Json(name = "openHours") val openHours : List<OpenHour>
) {

    fun formattedOpenHours(): String{
        var result = "asd"

        //for (openHourEntry in openHours){
        //    result = result + openHourEntry + "\n"
        //}

        return result
    }

    fun isOpenAsText(): String = if (isOpen){
        "Nyitva"
    } else{
        "Zárva"
    }

    @JsonClass(generateAdapter = true)
    data class OpenHour(
        @field:Json(name = "day") val day : Int,
        @field:Json(name = "openTime") val openTime: String,
        @field:Json(name = "closeTime") val closeTime: String
    )
}