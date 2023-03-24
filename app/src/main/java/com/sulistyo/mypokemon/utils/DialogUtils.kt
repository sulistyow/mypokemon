package com.sulistyo.mypokemon.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.sulistyo.mypokemon.R

object DialogUtils {

    fun Context.showFailedDialog() {
        AlertDialog.Builder(this).apply {
            setView(R.layout.view_failed_catch)
            setCancelable(true)
            setPositiveButton("OK") { p0, _ ->
                p0.dismiss()
            }
        }.create().show()
    }

    fun Context.showFailedReleaseDialog() {
        AlertDialog.Builder(this).apply {
            setView(R.layout.view_failed_release)
            setCancelable(true)
            setPositiveButton("OK") { p0, _ ->
                p0.dismiss()
            }
        }.create().show()
    }

    fun Context.showConfirmDialog(
        title: String?,
        message: String?,
        onclick: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OKE", onclick)
        builder.setNegativeButton(
            "CANCEL"
        ) { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

}