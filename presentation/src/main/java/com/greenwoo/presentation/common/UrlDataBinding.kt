package com.greenwoo.presentation.common

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber

@BindingAdapter(
    "url",
    "placeholder",
    requireAll = false
)
fun ImageView.loadBinding(url: String?, @DrawableRes placeholder: Int?) {

    postDelayed({
        Timber.d("loadBinding size $width")
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions
                    .circleCropTransform()
                    .apply {
                        if (placeholder != null) {
                            this.placeholder(placeholder)
                        }
                    }
            )
            .into(this)
    }, 150)
}