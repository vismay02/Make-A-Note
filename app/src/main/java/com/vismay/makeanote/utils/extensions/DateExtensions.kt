package com.vismay.makeanote.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

object DateExtensions {

    fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

}