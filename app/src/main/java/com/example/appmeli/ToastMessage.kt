package com.example.appmeli

import android.content.Context
import android.widget.Toast

object ToastMessage {
    fun show(context: Context,message:String, length:Int){
        Toast.makeText(
            context,
            message,
            length
        ).show()
    }
}