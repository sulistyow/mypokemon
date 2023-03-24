package com.sulistyo.mypokemon

import android.app.Application
import com.sulistyo.mypokemon.core.di.databaseModule
import com.sulistyo.mypokemon.core.di.networkModule
import com.sulistyo.mypokemon.core.di.repositoryModule
import com.sulistyo.mypokemon.di.useCaseModule
import com.sulistyo.mypokemon.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}