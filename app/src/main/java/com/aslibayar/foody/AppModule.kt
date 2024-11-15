package com.aslibayar.foody

import com.aslibayar.foody.ui.home.HomeScreenViewModel
import com.aslibayar.network.di.provideNetworkModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(provideNetworkModule)
    viewModel {
        HomeScreenViewModel(get())
    }
}