package com.aslibayar.foody.components.quickaccess

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aslibayar.foody.R
import com.aslibayar.foody.ui.theme.CustomTextStyle
import com.aslibayar.foody.ui.theme.Orange

@Composable
fun QuickAccess() {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 16.dp)
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Quick Access",
            style = CustomTextStyle.regularBlackLarge
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickAccessItem(icon = ImageVector.vectorResource(R.drawable.heart), title = "Favorite")
            QuickAccessItem(icon = ImageVector.vectorResource(R.drawable.recent), title = "Recent")
            QuickAccessItem(icon = ImageVector.vectorResource(R.drawable.vegan), title = "Vegan")
            QuickAccessItem(icon = ImageVector.vectorResource(R.drawable.meat), title = "Meat")
        }
    }
}

@Composable
fun QuickAccessItem(icon: ImageVector, title: String) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Orange),
            contentAlignment = Alignment.Center
        ) {
            Image(imageVector = icon, contentDescription = "", modifier = Modifier.size(20.dp))
        }
        Text(title, style = CustomTextStyle.regularBlackSmall)
    }
}