package com.sulistyo.mypokemon.di

import com.sulistyo.mypokemon.core.domain.usecase.IPokemonUseCases
import com.sulistyo.mypokemon.core.domain.usecase.PokemonInteractor
import com.sulistyo.mypokemon.presentation.mypokemon.MyPokemonViewModel
import com.sulistyo.mypokemon.presentation.pokemondetail.PokemonDetailViewModel
import com.sulistyo.mypokemon.presentation.pokemonlist.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    single<IPokemonUseCases> {
        PokemonInteractor(get())
    }
}

val viewModelModule = module {
    viewModel { PokemonListViewModel(get()) }
    viewModel { PokemonDetailViewModel(get()) }
    viewModel { MyPokemonViewModel(get()) }
}