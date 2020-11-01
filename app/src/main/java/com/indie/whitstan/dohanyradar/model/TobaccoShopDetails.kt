package com.indie.whitstan.dohanyradar.model

import android.os.Build

import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

import com.indie.whitstan.dohanyradar.utils.getDayStringByNumber
import com.indie.whitstan.dohanyradar.utils.getNextOpenHourByOpenHoursList
import com.indie.whitstan.dohanyradar.utils.getOpenHoursFormatted

@Entity
@JsonClass(generateAdapter = true)
data class TobaccoShopDetails(
        @field:Json(name = "id") @PrimaryKey val id: Int,
        @field:Json(name = "name") val name: String,
        @field:Json(name = "address") val address: String,
        @field:Json(name = "description") val description: String,
        @field:Json(name = "isOpen") val isOpen: Boolean,
        @field:Json(name = "openHours") val openHours: List<OpenHour>
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formattedOpenHours(): String{
        var result = ""

        for (openHourEntry in openHours){
            result = result + getDayStringByNumber(openHourEntry.day) + "\t\t" + getOpenHoursFormatted(openHourEntry)
        }

        return "Nyitvatartás:\n\n" + result
    }

    fun isOpenAsText(): String = if (isOpen){
        "Jelenleg Nyitva"
    } else{
        "Jelenleg Zárva\nKövetkező nyitás:\n" + getNextOpenHourByOpenHoursList(openHours)
    }

    @JsonClass(generateAdapter = true)
    data class OpenHour(
            @field:Json(name = "day") val day: Int,
            @field:Json(name = "openTime") val openTime: String,
            @field:Json(name = "closeTime") val closeTime: String
    )
}