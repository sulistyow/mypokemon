package com.sulistyo.mypokemon.core.data.repository

import androidx.paging.PagingData
import com.sulistyo.mypokemon.core.data.local.LocalDataSource
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import com.sulistyo.mypokemon.core.data.remote.PokemonDataSource
import com.sulistyo.mypokemon.core.data.remote.responses.ApiResponse
import com.sulistyo.mypokemon.core.data.remote.responses.Pokemon
import com.sulistyo.mypokemon.core.domain.repository.IPokemonRepository
import com.sulistyo.mypokemon.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PokemonRepository(
    private val localDataSource: LocalDataSource,
    private val pokemonDataSource: PokemonDataSource,
    private val appExecutors: AppExecutors
) : IPokemonRepository {
    override fun getPokemonList(): Flow<PagingData<PokemonListEntry>> {
        return pokemonDataSource.getPokemonList().flowOn(Dispatchers.IO)
    }

    override fun getPokemonInfo(pokemonName: String): Flow<ApiResponse<Pokemon>> =
        pokemonDataSource.getPokemonInfo(pokemonName).flowOn(Dispatchers.IO)

    override fun getMyPokemons(): Flow<List<PokemonListEntry>> {
        return localDataSource.getMyPokemons()
    }

    override fun insertPokemon(data: PokemonListEntry) {
        appExecutors.diskIO().execute {
            localDataSource.insertPokemon(data)
        }
    }

    override fun deletePokemon(data: PokemonListEntry) {
        appExecutors.diskIO().execute {
            localDataSource.deletePokemon(data)
        }
    }


}
