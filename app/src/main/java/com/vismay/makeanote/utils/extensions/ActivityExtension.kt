package com.vismay.makeanote.utils.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.vismay.makeanote.databinding.DialogAlertBinding

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

    fun AppCompatActivity.showAlertDialog(onPositiveButtonClick: () -> Unit) {
        this.let { activity ->

            val builder = AlertDialog.Builder(activity)

            // Use ViewBinding instead of inflating the layout manually
            val binding = DialogAlertBinding.inflate(LayoutInflater.from(activity))

            builder.setView(binding.root)
            val alertDialog = builder.create()
            alertDialog.show()

            // Access buttons using ViewBinding
            binding.buttonPositive.setOnClickListener {
                onPositiveButtonClick()
                alertDialog.dismiss()
            }

            binding.buttonNegative.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }

    fun AppCompatActivity.showKeyboard() =
        WindowCompat.getInsetsController(window, window.decorView).show(
            WindowInsetsCompat.Type.ime()
        )

    fun AppCompatActivity.hideKeyboard() =
        WindowCompat.getInsetsController(window, window.decorView)
            .hide(WindowInsetsCompat.Type.ime())
}