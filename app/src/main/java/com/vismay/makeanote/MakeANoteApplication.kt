package com.vismay.makeanote

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MakeANoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}