package com.aslibayar.foody

import com.aslibayar.data.di.provideDataModule
import com.aslibayar.foody.ui.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(provideDataModule)
    viewModel {
        HomeScreenViewModel(get())
    }
}