package com.vismay.makeanote.utils.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.dialog_alert.*


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

    fun AppCompatActivity.showDeleteDialog(onDeleteClick: (Boolean) -> Unit) {
        this.let {
            val builder = AlertDialog.Builder(it)
            val viewGroup: ViewGroup = it.findViewById(android.R.id.content)
            val dialogView: View =
                LayoutInflater.from(it)
                    .inflate(com.vismay.makeanote.R.layout.dialog_alert, viewGroup, false)
            builder.setView(dialogView)
            val alertDialog = builder.create()
            alertDialog.show()
            alertDialog.button_positive.setOnClickListener {
                onDeleteClick(true)
                alertDialog.dismiss()
            }
            alertDialog.button_negative.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }
}