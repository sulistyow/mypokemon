package com.sulistyo.mypokemon.core.di

import androidx.room.Room
import com.sulistyo.mypokemon.core.data.local.AppDatabase
import com.sulistyo.mypokemon.core.data.local.LocalDataSource
import com.sulistyo.mypokemon.core.data.remote.ApiServices
import com.sulistyo.mypokemon.core.data.remote.PagingDataSource
import com.sulistyo.mypokemon.core.data.remote.PokemonDataSource
import com.sulistyo.mypokemon.core.data.repository.PokemonRepository
import com.sulistyo.mypokemon.core.domain.repository.IPokemonRepository
import com.sulistyo.mypokemon.utils.AppExecutors
import com.sulistyo.mypokemon.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<AppDatabase>().pokemonDao }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "MyPokemon.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiServices::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { PokemonDataSource(get()) }
    factory { AppExecutors() }
    single<IPokemonRepository> { PokemonRepository(get(), get(), get()) }
}