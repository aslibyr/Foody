package com.aslibayar.foody.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.aslibayar.foody.ui.theme.CustomTextStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    modifier: Modifier,
    openRecipeDetailScreen: (recipeId: Int) -> Unit
) {
    val recipeList = viewModel.recipeList.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(recipeList.value.recipes) {
                it?.let {
                    ListItem(
                        modifier = Modifier
                            .clickable {
                                openRecipeDetailScreen(it.id)
                            }
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth(),
                        headlineContent = {
                            Text(
                                it.title,
                                style = CustomTextStyle.regularBlackLarge
                            )
                        },
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(8.dp))
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                AsyncImage(
                                    model = it.image, contentDescription = "", modifier = Modifier
                                        .height(80.dp)
                                        .width(100.dp), contentScale = ContentScale.Crop
                                )
                            }
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.White)
                    )
                }
            }
        }
    }
}
