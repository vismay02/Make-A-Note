package com.vismay.makeanote.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

object DateExtensions {

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm a", Locale.getDefault())
        return sdf.format(Date())
    }

    fun String.getFormattedDate(): String {
        return if (getCurrentDate().substringBefore(" ") == this.substringBefore(" ")) {
            this.substringAfter(" ")
        } else {
            this.substringBefore(" ")
        }
    }
}