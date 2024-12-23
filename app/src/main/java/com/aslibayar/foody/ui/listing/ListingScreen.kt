package com.aslibayar.foody.ui.listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aslibayar.foody.components.image_view.CustomImageView
import com.aslibayar.foody.components.loading.CustomLoading
import com.aslibayar.foody.ui.theme.CustomTextStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListingScreen(
    viewModel: ListingViewModel = koinViewModel(),
    openRecipeDetailScreen: (recipeId: Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        CustomLoading()
    } else {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.recipes) { recipe ->
                ListItem(
                    modifier = Modifier
                        .clickable {
                            openRecipeDetailScreen(recipe?.id ?: 0)
                        }
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    headlineContent = {
                        recipe?.let {
                            Text(
                                it.title,
                                style = CustomTextStyle.regularBlackLarge
                            )
                        }
                    },
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .shadow(elevation = 10.dp, shape = RoundedCornerShape(8.dp))
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            recipe?.image?.let {
                                CustomImageView(
                                    imageUrl = it, modifier = Modifier
                                        .height(80.dp)
                                        .width(100.dp), contentScale = ContentScale.Crop
                                )
                            }
                        }
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.White)
                )
            }
        }
    }
}
