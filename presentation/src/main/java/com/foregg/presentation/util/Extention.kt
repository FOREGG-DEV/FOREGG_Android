package com.foregg.presentation.util

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import java.io.Serializable

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")
fun String.toList() : List<String> = this.split(", ").map { it.trim() }

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}