package com.aslibayar.foody.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aslibayar.foody.components.image_view.CustomImageView
import com.aslibayar.foody.components.textfield.CustomOutlinedTextField
import com.aslibayar.foody.ui.theme.CustomTextStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel = koinViewModel()) {
    var text by rememberSaveable {
        mutableStateOf("")
    }

    val recipeList by viewModel.recipeList.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    var hasFocus by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .focusRequester(focusRequester = focusRequester)
            .clickable {
                if (hasFocus) {
                    focusManager.clearFocus()
                }
            }
    ) {
        CustomOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
                .onFocusChanged {
                    hasFocus = it.hasFocus
                },
            label = "Search",
            text = text,
            returnText = { text = it },
            onImeClicked = {
                viewModel.searchRecipe(text)
                hasFocus = false
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
        )
        if (text.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "",
                    Modifier.size(50.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    text = "Search something to start.",
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(recipeList.recipes) {
                    it?.let {
                        ListItem(
                            modifier = Modifier
                                .clickable {
//                                openRecipeDetailScreen(it.id)
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
                                    CustomImageView(
                                        imageUrl = it.image, modifier = Modifier
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
}