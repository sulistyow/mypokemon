package com.sulistyo.mypokemon.core.domain.usecase

import androidx.paging.PagingData
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import com.sulistyo.mypokemon.core.data.remote.responses.ApiResponse
import com.sulistyo.mypokemon.core.data.remote.responses.Pokemon
import kotlinx.coroutines.flow.Flow

interface IPokemonUseCases {
    fun getPokemonList(): Flow<PagingData<PokemonListEntry>>
    fun getPokemonInfo(pokemonName: String): Flow<ApiResponse<Pokemon>>
    fun getMyPokemons(): Flow<List<PokemonListEntry>>
    fun insertPokemon(data: PokemonListEntry)
    fun deletePokemon(data: PokemonListEntry)
}