package com.greenwoo.presentation.common

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

@BindingAdapter(
    "spannableText",
    "spannableStart",
    "spannableEnd",
    "spannableColor",
    requireAll = false
)
fun bindSpannable(
    view: TextView,
    spannableText: String?,
    spannableStart: Int?,
    spannableEnd: Int?,
    spannableColor: Int?
) {
    if (spannableText != null && spannableStart != null && spannableColor != null) {
        val wordToSpan: Spannable =
            SpannableString(spannableText)
        val indexEnd = spannableEnd ?: spannableText.length
        if (indexEnd > spannableStart)
            wordToSpan.setSpan(
                ForegroundColorSpan(spannableColor),
                spannableStart,
                indexEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        view.text = wordToSpan
    }
}

@BindingAdapter("textRes")
fun bindTextRes(view: TextView, @StringRes textRes: Int?) {
    textRes?.let {
        view.setText(textRes)
    }
}

@BindingAdapter("android:visibility", "invisibilityMode", requireAll = false)
fun bindVisibility(view: View, visibility: Boolean?, invisibleMode: Int?) {
    if (visibility == null) {
        view.visibility = invisibleMode ?: View.INVISIBLE
        return
    }

    if (invisibleMode != null && invisibleMode != View.INVISIBLE && invisibleMode != View.GONE) {
        throw IllegalArgumentException("Invisibility mode must be one of View.INVISIBILE or View.GONE")
    }

    view.visibility = if (visibility) View.VISIBLE else invisibleMode ?: View.INVISIBLE
}