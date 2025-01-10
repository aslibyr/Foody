package com.aslibayar.foody

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.noRippleClick(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        onClick()
    }
}

fun Modifier.widthPercent(percent: Float, configuration: Configuration): Modifier {
    val screenWidth = configuration.screenWidthDp.dp
    return width(screenWidth * percent)
}


fun Modifier.heightPercent(percent: Float, configuration: Configuration): Modifier {
    val screenHeight = configuration.screenHeightDp.dp
    return height(screenHeight * percent)
}

fun heightPercent(percent: Float, configuration: Configuration): Dp {
    val screenHeight = configuration.screenHeightDp.dp
    return screenHeight * percent
}

fun Modifier.percent(
    configuration: Configuration,
    widthPercent: Float? = null,
    heightPercent: Float? = null
): Modifier {
    val modifier = this
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val newWidth = widthPercent?.let { screenWidth * widthPercent }
    val newHeight = heightPercent?.let { screenHeight * heightPercent }
    newWidth?.let { modifier.width(newWidth) }
    newHeight?.let { modifier.height(newHeight) }
    return modifier
}