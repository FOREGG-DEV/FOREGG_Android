package com.foregg.presentation.util

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

object SvgImageUtil {

    fun loadImageFromUrl(imageView : ImageView, imageUrl: String, context : Context) {
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        val request = ImageRequest.Builder(context)
            .data(imageUrl) // SVG 이미지 URL
            .target(imageView)
            .build()

        imageLoader.enqueue(request)
    }
}