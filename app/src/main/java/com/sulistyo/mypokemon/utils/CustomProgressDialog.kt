package com.sulistyo.mypokemon.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.sulistyo.mypokemon.R

class CustomProgressDialog {

    lateinit var dialog :CustomDialog

    fun show(context: Context): Dialog {
        return show(context, null)
    }

    fun show(context: Context, title: CharSequence?): Dialog {

        dialog = CustomDialog(context)
        dialog.setContentView(R.layout.view_loading_pokemon)
        dialog.show()
        return dialog
    }

    fun hide(){
        dialog.dismiss()
    }


    class CustomDialog(context: Context) : Dialog(context) {
        init {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}