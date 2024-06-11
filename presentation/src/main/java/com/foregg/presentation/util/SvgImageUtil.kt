package com.foregg.presentation.util

import android.content.Context
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

object SvgImageUtil {

    fun loadImageFromUrl(
        imageView : ImageView,
        imageUrl: String,
        context : Context,
        loadingDrawable : Int? = null,
        errorDrawable: Int? = null
    ) {
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        val requestBuilder = ImageRequest.Builder(context)
            .data(imageUrl)
            .target(imageView)

        loadingDrawable?.let {
            requestBuilder.placeholder(it)
        }

        errorDrawable?.let {
            requestBuilder.error(it)
        }

        imageLoader.enqueue(requestBuilder.build())
    }
}