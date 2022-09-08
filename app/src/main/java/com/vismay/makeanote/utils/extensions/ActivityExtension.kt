package com.vismay.makeanote.utils.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle

object ActivityExtension {

    inline fun <reified T:Any>Context.launchActivity(
        bundle: Bundle?=null
    ){
        val intent = Intent(this, T::class.java)
        startActivity(intent,bundle)
    }
}