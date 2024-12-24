package com.aslibayar.foody.components.image_view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.aslibayar.foody.R
import com.aslibayar.foody.ui.theme.Orange

@Composable
fun CustomImageView(
    imageUrl: String,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    @DrawableRes errorResource: Int = R.drawable.error,
) {
    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .respectCacheHeaders(false)
        .build()
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .memoryCacheKey(imageUrl)
            .diskCacheKey(imageUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .allowHardware(true)
            .build(),
        imageLoader = imageLoader,
        contentDescription = "",
        loading = {
            CircularProgressIndicator(color = Orange)
        },
        modifier = modifier,
        error = {
            Image(painter = painterResource(id = errorResource), contentDescription = "error")
        },
        contentScale = contentScale
    )
}