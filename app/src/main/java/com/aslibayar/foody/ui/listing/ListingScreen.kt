package com.aslibayar.foody.ui.listing

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListingScreen(viewModel: ListingViewModel = koinViewModel()) {
    val screenType = viewModel.screenType
    Text(screenType.name)

}