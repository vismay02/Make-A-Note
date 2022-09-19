package com.vismay.makeanote.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


object Utils {

    fun showShortToast(context: Context, @StringRes stringId: Int) {
        Toast.makeText(context, context.getString(stringId), Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}