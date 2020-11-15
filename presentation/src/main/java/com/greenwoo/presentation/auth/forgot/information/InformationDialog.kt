package com.greenwoo.presentation.auth.forgot.information

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.greenwoo.presentation.R
import kotlinx.android.synthetic.main.dialog_information.view.*

class InformationDialog(context: Context, private val onClickConfirm: () -> Unit) {

    private val dialog: AlertDialog

    init {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.dialog_information,
            null,
            false
        )

        dialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(view)
            .create()

        view.btnConfirm.setOnClickListener {
            onClickConfirm.invoke()
            dialog.dismiss()
        }

        dialog.show()
    }
}