package com.aslibayar.foody.ui.detail.components.Ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aslibayar.data.model.RecipeIngredientsUIModel
import com.aslibayar.foody.components.image_view.CustomImageView
import com.aslibayar.foody.noRippleClick

@Composable
fun RecipeIngredientsItem(modifier: Modifier = Modifier, item: RecipeIngredientsUIModel) {
    if (item.name.isNotEmpty() || (item.image.isNotEmpty())) {
        Column(
            modifier
                .fillMaxWidth(0.33f)
                .padding(4.dp)
                .noRippleClick {

                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomImageView(
                imageUrl = item.image,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit,
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = item.original.lowercase().split(" ")
                    .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } },
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}