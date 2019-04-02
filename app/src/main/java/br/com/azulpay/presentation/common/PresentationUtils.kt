package br.com.azulpay.presentation.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions

const val PHONE_MASK = "(##) #####-####"

fun ImageView.setCircleImage(context: Context, imageUrl: String) {
    GlideApp.with(context)
            .asBitmap()
            .apply(RequestOptions.circleCropTransform())
            .load(imageUrl)
            .into(this)
}