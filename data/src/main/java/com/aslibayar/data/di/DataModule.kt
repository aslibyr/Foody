package com.aslibayar.data.di

import com.aslibayar.data.local.AppDatabase
import com.aslibayar.data.repository.RecipeRepository
import com.aslibayar.network.di.provideNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val provideDataModule = module {
    includes(provideNetworkModule)
    single { RecipeRepository(get(), get()) }
    single { AppDatabase.getDatabase(androidContext()) }
}
