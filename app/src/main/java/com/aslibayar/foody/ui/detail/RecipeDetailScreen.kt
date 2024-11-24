package com.aslibayar.foody.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.aslibayar.foody.components.html_text.HtmlText
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier, viewModel: RecipeDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {

        AsyncImage(
            model = uiState.recipe.image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 160.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.recipe.title?.let { title ->
                Text(
                    text = if (title.length > 35) title.take(35) + "..." else title, // Karakter sınırı ve üç nokta ekleme
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
            uiState.recipe.summary?.let { HtmlText(html = it, modifier = Modifier.padding(16.dp)) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text =
                    "Instructions",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,

                    )
            }
            uiState.recipe.instructions?.let {
                HtmlText(
                    html = it,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}