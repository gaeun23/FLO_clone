package com.example.flo.presentation.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun Context.imgRadiusUtil(img: Int, imgView: View) {
    Glide.with(this)
        .load(img)
        .transform(CenterCrop(), RoundedCorners(6))
        .into(imgView as ImageView)
}