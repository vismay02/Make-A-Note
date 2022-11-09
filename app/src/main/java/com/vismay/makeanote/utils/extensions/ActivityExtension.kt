package com.vismay.makeanote.utils.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.vismay.makeanote.R

object ActivityExtension {

    inline fun <reified T : Any> Context.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}
    ) {
        val intent = newIntent<T>(this)
        intent.init()
        startActivity(intent, options)
    }

    inline fun <reified T : Any> newIntent(context: Context): Intent =
        Intent(context, T::class.java)

    fun Context.showDeleteDialog(onDeleteClick: (Boolean) -> Unit) {
        this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(
                    R.string.delete_all
                ) { dialog, id ->
                    onDeleteClick(true)
                    dialog.dismiss()
                }
                setNegativeButton(
                    R.string.cancel_all
                ) { dialog, id ->
                    dialog.dismiss()
                }
            }
            builder.setMessage(R.string.delete_message)
            builder.create()
            builder.show()
        }
    }
}