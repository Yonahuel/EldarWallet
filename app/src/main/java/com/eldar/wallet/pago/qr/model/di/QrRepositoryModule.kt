package com.eldar.wallet.pago.qr.model.di

import com.eldar.wallet.common.network.DataDownloader
import com.eldar.wallet.pago.qr.repositories.QrRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class QrRepositoryModule {
    @Provides
    @Singleton
    fun provideQrRepository(downloader: DataDownloader): QrRepository { return QrRepository(downloader) }
}