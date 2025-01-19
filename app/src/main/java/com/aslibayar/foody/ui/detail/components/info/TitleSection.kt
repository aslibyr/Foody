package com.aslibayar.foody.ui.detail.components.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.foody.ui.theme.CustomTextStyle

@Composable
fun TitleSection(recipe: RecipeDetailUIModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = recipe.title.lowercase().split(" ")
                .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 32.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = CustomTextStyle.regularBlackXLarge
        )
    }
}