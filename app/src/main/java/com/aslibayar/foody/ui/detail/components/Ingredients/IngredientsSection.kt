package com.aslibayar.foody.ui.detail.components.Ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.foody.ui.theme.CustomTextStyle
import com.aslibayar.foody.ui.theme.Orange

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IngredientsSection(recipe: RecipeDetailUIModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Ingredients",
            color = Orange,
            style = CustomTextStyle.regularBlackLarge
        )
        FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
            recipe.extendedIngredients.distinctBy { it.name }.forEach {
                RecipeIngredientsItem(item = it)
            }
        }
    }
}