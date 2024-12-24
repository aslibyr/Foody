package com.aslibayar.foody.components.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aslibayar.data.mapper.toWidgetModel
import com.aslibayar.data.model.RecipeWidgetModel
import com.aslibayar.foody.components.image_view.CustomImageView

@Composable
fun RecipeWidget(
    model: RecipeWidgetComponentModel,
    openListScreen: () -> Unit,
    onRecipeClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { openListScreen() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "View all",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 8.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        LazyRow(Modifier.fillMaxWidth(), contentPadding = PaddingValues(start = 16.dp)) {
            items(model.recipes) {
                it?.let { it1 ->
                    RecipeWidgetItem(
                        recipe = it1.toWidgetModel(),
                        onRecipeClick = onRecipeClick
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeWidgetItem(
    recipe: RecipeWidgetModel,
    onRecipeClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                onRecipeClick(recipe.id.toString())
            }
            .padding(8.dp)
            .width(150.dp)
            .shadow(elevation = 8.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(),
    ) {
        Column(
            modifier = Modifier.wrapContentHeight()
        ) {
            CustomImageView(
                imageUrl = recipe.image,
                modifier = Modifier.wrapContentHeight(),
                contentScale = ContentScale.Crop
            )
            // Title
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
            )
        }
    }
}

