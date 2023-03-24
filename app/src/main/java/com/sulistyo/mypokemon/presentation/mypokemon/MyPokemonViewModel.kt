package com.sulistyo.mypokemon.presentation.mypokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import com.sulistyo.mypokemon.core.domain.usecase.IPokemonUseCases

class MyPokemonViewModel(
    private val useCases: IPokemonUseCases
) : ViewModel() {

    val myPokemons = useCases.getMyPokemons().asLiveData()

    fun insertPokemon(pokemon: PokemonListEntry) = useCases.insertPokemon(pokemon)

    fun deletePokemon(pokemon: PokemonListEntry) = useCases.deletePokemon(pokemon)
}