package com.aslibayar.data.di

import com.aslibayar.data.local.AppDatabase
import com.aslibayar.data.repository.AIRepository
import com.aslibayar.data.repository.AIRepositoryImpl
import com.aslibayar.data.repository.RecipeRepository
import com.aslibayar.data.repository.RecipeRepositoryImpl
import com.aslibayar.network.di.provideNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val provideDataModule = module {
    includes(provideNetworkModule)
    single { AppDatabase.getInstance(androidContext()) }
    single<RecipeRepository> { RecipeRepositoryImpl(get(), get()) }
    single<AIRepository> { AIRepositoryImpl(get()) }
}
