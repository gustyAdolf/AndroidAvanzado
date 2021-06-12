package com.simplegapps.poorinsta

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

fun Boolean.alsoIfTrue(cb: () -> Unit) {
    if (this) cb()
}

fun ImageView.load(url: String, op: (RequestBuilder<*>) -> Unit = {}) {
    Glide.with(this).load(url).also { op(it) }.into(this)
}