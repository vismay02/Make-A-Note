package com.vismay.makeanote

import android.app.Application
import com.vismay.makeanote.di.component.ApplicationComponent
import com.vismay.makeanote.di.component.DaggerApplicationComponent
import com.vismay.makeanote.di.module.ApplicationModule

class MakeANoteApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}