package com.aslibayar.foody.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aslibayar.foody.noRippleClick

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .size(40.dp)
            .shadow(
                elevation = 8.dp,
                shape = CircleShape
            )
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .noRippleClick { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = "Favorite",
            modifier = Modifier.size(24.dp),
            tint = if (isFavorite) Color.Red else Color.Black
        )
    }
}