package com.dashagy.footballapp

import android.content.Context
import android.widget.Toast

object AppUtil {
    fun showMessage(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}