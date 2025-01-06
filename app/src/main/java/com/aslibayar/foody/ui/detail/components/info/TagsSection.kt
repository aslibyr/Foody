package com.aslibayar.foody.ui.detail.components.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.foody.ui.theme.CustomTextStyle
import com.aslibayar.foody.ui.theme.LightOrange

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsSection(recipe: RecipeDetailUIModel) {
    if (recipe.diets.isNotEmpty()) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            recipe.diets.forEach {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(LightOrange), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.lowercase().split(" ")
                            .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } },
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        style = CustomTextStyle.regularBlackMedium
                    )
                }
            }
        }
    }
}