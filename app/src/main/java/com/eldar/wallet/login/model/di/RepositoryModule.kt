package com.eldar.wallet.login.model.di

import com.eldar.wallet.login.repositories.UsuarioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUsuarioRepository(db: Realm): UsuarioRepository { return UsuarioRepository(db) }
}