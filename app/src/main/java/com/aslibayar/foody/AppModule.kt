package com.aslibayar.foody

import androidx.lifecycle.SavedStateHandle
import com.aslibayar.data.di.provideDataModule
import com.aslibayar.foody.ui.detail.RecipeDetailViewModel
import com.aslibayar.foody.ui.home.HomeScreenViewModel
import com.aslibayar.foody.ui.listing.ListingViewModel
import com.aslibayar.foody.ui.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(provideDataModule)
    viewModel {
        HomeScreenViewModel(get())
    }
    viewModel { (handle: SavedStateHandle) ->
        RecipeDetailViewModel(handle, get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel { (handle: SavedStateHandle) ->
        ListingViewModel(handle)
    }
}