package com.vismay.makeanote.utils.extensions

import android.view.View
import android.view.View.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object ViewExtensions {

    fun AppCompatEditText.onDone(callback: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                callback.invoke()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    fun View.visibleGone(isGone: Boolean) {
        visibility = if (isGone) GONE
        else VISIBLE
    }

    fun View.visibleInvisible(isInvisible: Boolean = false) {
        visibility = if (isInvisible) INVISIBLE
        else VISIBLE
    }

    fun View.clicks() = callbackFlow<Unit> {
        setOnClickListener {
            trySend(Unit)
        }
        awaitClose { setOnClickListener(null) }
    }
}