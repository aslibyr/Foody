package com.aslibayar.foody.ui.detail.components.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.foody.R
import com.aslibayar.foody.ui.theme.CustomTextStyle

@Composable
fun InfoSection(recipe: RecipeDetailUIModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        recipe.time.takeIf { it.isNotEmpty() }?.let {
            IconWithText(
                iconId = R.drawable.clock,
                text = it,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        recipe.servings.takeIf { it.isNotEmpty() }?.let {
            IconWithText(
                iconId = R.drawable.serving,
                text = "$it servings",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
fun IconWithText(iconId: Int, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = Color.Gray
        )
        Text(
            text,
            style = CustomTextStyle.regularBlackMedium,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
