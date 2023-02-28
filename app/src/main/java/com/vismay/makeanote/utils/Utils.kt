package com.vismay.makeanote.utils

import android.content.Context
import android.widget.Toast
import com.vismay.makeanote.R

object Utils {

    fun showShortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun randomColorGenerator(): Int {
        return arrayOf(
            R.color.electric_blue,
            R.color.old_lace,
            R.color.vanilla,
            R.color.munsell,
            R.color.almond,
            R.color.pale_dogwood,
        ).random()
    }

}