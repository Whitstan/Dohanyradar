package com.indie.whitstan.dohanyradar.utils

import com.indie.whitstan.dohanyradar.model.TobaccoShopDetails
import java.util.*

fun getDayStringByNumber(dayNumber: Int) : String{
    return when (dayNumber) {
        0 -> "Hétfő"
        1 -> "Kedd"
        2 -> "Szerda"
        3 -> "Csütörtök"
        4 -> "Péntek"
        5 -> "Szombat"
        6 -> "Vasárnap"
        else -> {
            "Ismeretlen nap"
        }
    }
}

fun getNextOpenHourByOpenHoursList(openHoursList : List<TobaccoShopDetails.OpenHour>) : String{
    val nextDayNumber = getNextDayNumber()
    return if (nextDayNumber != 100) {
        getDayStringByNumber(nextDayNumber) + ": " + getOpenHoursFormatted(openHoursList[nextDayNumber]).substring(0, 5)
    } else{
        "Hiányzó nyitvatartás adat"
    }
}

fun getNextDayNumber(): Int{
    val calendar: Calendar = Calendar.getInstance()
    when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> {
            return 0
        }
        Calendar.MONDAY -> {
            return 1
        }
        Calendar.TUESDAY -> {
            return 2
        }
        Calendar.WEDNESDAY -> {
            return 3
        }
        Calendar.THURSDAY -> {
            return 4
        }
        Calendar.FRIDAY -> {
            return 5
        }
        Calendar.SATURDAY -> {
            return 6
        }
        else -> {
            return 100
        }
    }
}

fun getOpenHoursFormatted(openHourEntry : TobaccoShopDetails.OpenHour) : String{
    return openHourEntry.openTime.substring(11, 16) + " - " + openHourEntry.closeTime.substring(11, 16) + "\n"
}

