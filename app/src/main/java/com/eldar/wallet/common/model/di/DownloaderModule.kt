package com.eldar.wallet.common.model.di

import com.eldar.wallet.common.network.DataDownloader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DownloaderModule {
    @Provides
    @Singleton
    fun provideDataDownloader(): DataDownloader { return DataDownloader() }
}