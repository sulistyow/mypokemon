package com.sulistyo.mypokemon.core.domain.usecase

import androidx.paging.PagingData
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import com.sulistyo.mypokemon.core.data.remote.responses.ApiResponse
import com.sulistyo.mypokemon.core.data.remote.responses.Pokemon
import com.sulistyo.mypokemon.core.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonInteractor(
    private val repository: IPokemonRepository
) : IPokemonUseCases {
    override fun getPokemonList(): Flow<PagingData<PokemonListEntry>> =
        repository.getPokemonList()

    override fun getPokemonInfo(pokemonName: String): Flow<ApiResponse<Pokemon>> =
        repository.getPokemonInfo(pokemonName)

    override fun getMyPokemons(): Flow<List<PokemonListEntry>> =
        repository.getMyPokemons()

    override fun insertPokemon(data: PokemonListEntry) = repository.insertPokemon(data)

    override fun deletePokemon(data: PokemonListEntry) = repository.deletePokemon(data)
}