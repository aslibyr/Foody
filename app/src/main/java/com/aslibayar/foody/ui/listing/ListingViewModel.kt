package com.aslibayar.foody.ui.listing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.aslibayar.foody.ListingRoute

class ListingViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val screenType = savedStateHandle.toRoute<ListingRoute>().screenType

}