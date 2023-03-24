package com.sulistyo.mypokemon.presentation.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import com.sulistyo.mypokemon.core.domain.usecase.IPokemonUseCases

class PokemonDetailViewModel(private val useCases: IPokemonUseCases) : ViewModel() {

    fun getPokemonInfo(pokemonName: String) =
        useCases.getPokemonInfo(pokemonName = pokemonName).asLiveData()

    fun insertPokemon(pokemon: PokemonListEntry) = useCases.insertPokemon(pokemon)

}