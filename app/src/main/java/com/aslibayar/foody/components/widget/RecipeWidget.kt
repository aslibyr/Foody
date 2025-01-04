package com.aslibayar.foody.components.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.foody.R
import com.aslibayar.foody.components.image_view.CustomImageView
import com.aslibayar.foody.ui.theme.CustomTextStyle
import com.aslibayar.foody.ui.theme.Gray

@Composable
fun RecipeWidget(
    model: RecipeWidgetComponentModel,
    openListScreen: () -> Unit,
    openRecipeDetailScreen: (recipeId: Int) -> Unit,
    itemWidth: Dp = 150.dp,
    itemHeight: Dp = 120.dp,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(start = 16.dp)
                .clickable { openListScreen() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = model.widgetCategory,
                style = CustomTextStyle.regularBlackLarge
            )
            Text(
                text = "View all",
                style = CustomTextStyle.regularBlackMedium
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .padding(start = 8.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        LazyRow(
            Modifier.fillMaxWidth(),
        ) {
            items(model.recipes) { recipe ->
                recipe?.let {
                    RecipeWidgetItem(
                        recipe = it,
                        width = itemWidth,
                        height = itemHeight,
                        onRecipeClick = { openRecipeDetailScreen(recipe.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeWidgetItem(
    recipe: RecipeUIModel,
    onRecipeClick: (recipeId: Int) -> Unit,
    width: Dp,
    height: Dp
) {
    Card(
        modifier = Modifier
            .width(width)
            .wrapContentSize()
            .background(Color.White)
            .clickable {
                onRecipeClick(recipe.id)
            }
            .padding(8.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(),
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
        ) {
            CustomImageView(
                imageUrl = recipe.image,
                modifier = Modifier
                    .width(width)
                    .height(height),
                contentScale = ContentScale.Crop
            )
            Text(
                text = recipe.title,
                style = CustomTextStyle.regularBlackMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.clock),
                    contentDescription = "",
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 4.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = recipe.readyInMinutes,
                    fontWeight = FontWeight.Light,
                    style = CustomTextStyle.regularBlackMedium,
                    color = Gray,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

