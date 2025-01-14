package com.aslibayar.foody.ui.listing

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aslibayar.data.model.RecipeUIModel
import com.aslibayar.foody.R
import com.aslibayar.foody.components.button.ListResetButton
import com.aslibayar.foody.components.image_view.CustomImageView
import com.aslibayar.foody.components.loading.CustomLoading
import com.aslibayar.foody.components.pull_to_refresh.PullToRefreshBox
import com.aslibayar.foody.components.topbar.TopBarComponent
import com.aslibayar.foody.ui.common.EmptyScreen
import com.aslibayar.foody.ui.theme.CustomTextStyle
import com.aslibayar.foody.ui.theme.Gray
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingScreen(
    viewModel: ListingViewModel = koinViewModel(),
    openRecipeDetailScreen: (recipeId: Int) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val showResetButton by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 0
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ListingEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is ListingEffect.NavigateToDetail -> {
                    openRecipeDetailScreen(effect.recipeId)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarComponent(
                title = uiState.screenType.widgetType,
                onBackClick = onBackClick
            )

            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = { viewModel.onPullToRefresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                if (uiState.isLoading) {
                    CustomLoading()
                } else if (uiState.recipes.isEmpty()) {
                    when (uiState.screenType) {
                        ScreenType.FAVORITE -> EmptyScreen(
                            icon = R.drawable.heart,
                            title = "No Favorite Recipes Yet",
                        )

                        ScreenType.VEGAN -> EmptyScreen(
                            icon = R.drawable.vegan,
                            title = "No Vegan Recipes Found",
                        )

                        ScreenType.GLUTEN_FREE -> EmptyScreen(
                            icon = R.drawable.gluten_free,
                            title = "No Gluten-Free Recipes Found",
                        )

                        else -> EmptyScreen(
                            icon = R.drawable.search,
                            title = "No Recipes Found",
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 80.dp
                        ),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(uiState.recipes) { recipe ->
                            recipe?.let {
                                RecipeItem(
                                    recipe = it,
                                    onRecipeClick = { recipeId ->
                                        viewModel.onEvent(ListingEvent.OpenRecipeDetail(recipeId))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showResetButton) {
            ListResetButton {
                coroutineScope.launch {
                    gridState.animateScrollToItem(0)
                }
            }
        }
    }
}


@Composable
fun RecipeItem(
    recipe: RecipeUIModel,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onRecipeClick(recipe.id) }
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            CustomImageView(
                imageUrl = recipe.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = recipe.title,
                    style = CustomTextStyle.regularBlackMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.clock),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(end = 4.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = recipe.readyInMinutes,
                        style = CustomTextStyle.regularBlackMedium,
                        color = Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}