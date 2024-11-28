package com.aslibayar.foody.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.aslibayar.data.model.BaseUIModel
import com.aslibayar.data.model.RecipeDetailUIModel
import com.aslibayar.data.model.RecipeIngredientsUIModel
import com.aslibayar.foody.components.html_text.HtmlText
import com.aslibayar.foody.components.image_view.CustomImageView
import com.aslibayar.foody.components.loading.CustomLoading
import com.aslibayar.foody.noRippleClick
import com.aslibayar.foody.ui.theme.CustomTextStyle
import com.aslibayar.foody.ui.theme.LightOrange
import com.aslibayar.foody.ui.theme.Orange
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel = koinViewModel()
) {
    val recipe by viewModel.recipe.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        when (recipe) {
            is BaseUIModel.Error -> {}
            BaseUIModel.Loading -> {
                CustomLoading()
            }

            is BaseUIModel.Success -> {
                val recipeData = (recipe as BaseUIModel.Success<RecipeDetailUIModel>).data
                StatelessRecipeDetail(recipe = recipeData)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StatelessRecipeDetail(modifier: Modifier = Modifier, recipe: RecipeDetailUIModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = recipe.image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 180.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
                )
                .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                .background(Color.White),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = Orange),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 32.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = CustomTextStyle.regularBlackXLarge
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (recipe.time.isNotEmpty() && recipe.servings.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (recipe.time.isNotEmpty()) {
                            Text(
                                "Preperation time: " + recipe.time,
                                style = CustomTextStyle.regularBlackSmall
                            )
                        }
                        if (recipe.servings.isNotEmpty()) {
                            Text(
                                "Servings: " + recipe.servings,
                                style = CustomTextStyle.regularBlackSmall
                            )
                        }
                    }
                }
                HtmlText(
                    html = recipe.summary,
                    textStyle = CustomTextStyle.regularBlackMedium
                )
                if (recipe.diets.isNotEmpty()) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        recipe.diets.forEach {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(LightOrange),
                                contentAlignment = Alignment.Center
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

                if (recipe.instructions.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Instructions",
                            color = Orange,
                            style = CustomTextStyle.regularBlackLarge
                        )
                    }
                    HtmlText(
                        html = recipe.instructions,
                        textStyle = CustomTextStyle.regularBlackMedium
                    )
                }
            }
            Text(
                "Ingredients",
                style = CustomTextStyle.regularBlackLarge, color = Orange,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
            FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
                recipe.extendedIngredients.distinctBy { it.name }.forEach {
                    IngredientsItem(item = it)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun IngredientsItem(modifier: Modifier = Modifier, item: RecipeIngredientsUIModel) {
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
                text = item.name.lowercase().split(" ")
                    .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } },
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}