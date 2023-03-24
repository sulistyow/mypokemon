package com.sulistyo.mypokemon.utils

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.sulistyo.mypokemon.R
import com.sulistyo.mypokemon.databinding.ViewCapturedPokemonBinding

class CustomDialogFragment(
    private val url: String,
    private val isRename: Boolean? = false,
    private val newName: String? = null,
    private val onSubmitListener: (String) -> Unit
) : DialogFragment() {

    private lateinit var bind: ViewCapturedPokemonBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bind = ViewCapturedPokemonBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bind.root)

        if (isRename == true) {
            bind.etNickname.isEnabled = false
            bind.etNickname.setText(newName)
        } else {
            bind.etNickname.isEnabled = true
        }

        Glide.with(this).load(url).placeholder(R.drawable.poke_ball).into(bind.ivPokemon)
        bind.btSave.setOnClickListener {

            if (bind.etNickname.text.isBlank()) {
                bind.etNickname.error = "Enter NickName"
            } else {
                onSubmitListener.invoke(bind.etNickname.text.toString())
                dismiss()
            }
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        return dialog
    }

}