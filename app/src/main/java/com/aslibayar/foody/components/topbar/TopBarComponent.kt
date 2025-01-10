package com.aslibayar.foody.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aslibayar.foody.heightPercent
import com.aslibayar.foody.ui.theme.CustomTextStyle

@Composable
fun TopBarComponent(
    title: String,
    onBackClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .height(
                heightPercent(
                    0.06f,
                    configuration = configuration
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(7f),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onBackClick()
                    }
                    .height(IntrinsicSize.Min),
                tint = Color.Black
            )
        }
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(0.90f),
            style = CustomTextStyle.regularBlackLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}