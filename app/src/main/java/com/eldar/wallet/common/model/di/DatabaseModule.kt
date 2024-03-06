package com.eldar.wallet.common.model.di

import com.eldar.wallet.login.model.entities.Usuario
import com.eldar.wallet.tarjeta.model.entities.Tarjeta
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDb(): Realm {
        val config= RealmConfiguration.Builder(
            schema = setOf(
                Usuario::class,
                Tarjeta::class
            )
        )
            .name("app_database")
            .schemaVersion(1)
            .build()

        return Realm.open(config)
    }
}