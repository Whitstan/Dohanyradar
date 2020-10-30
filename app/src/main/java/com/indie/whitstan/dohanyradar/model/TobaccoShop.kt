package com.indie.whitstan.dohanyradar.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class TobaccoShop(
    @field:Json(name = "id") @PrimaryKey val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "address") val address: String,
    @field:Json(name = "latitude") val latitude: Double,
    @field:Json(name = "longitude") val longitude: Double,
    @field:Json(name = "isOpen") val isOpen: Boolean
) : Parcelable {

    fun isOpenAsText(): String = if (isOpen){
        "Nyitva"
    } else{
        "Zárva"
    }
}