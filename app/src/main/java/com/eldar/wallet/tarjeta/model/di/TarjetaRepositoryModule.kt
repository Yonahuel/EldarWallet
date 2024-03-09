package com.eldar.wallet.tarjeta.model.di

import com.eldar.wallet.tarjeta.repositories.TarjetaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TarjetaRepositoryModule {
    @Provides
    @Singleton
    fun provideTarjetaRepository(db: Realm): TarjetaRepository { return TarjetaRepository(db)}
}