package com.sulistyo.mypokemon.core.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sulistyo.mypokemon.core.data.remote.responses.ApiResponse
import com.sulistyo.mypokemon.core.data.remote.responses.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonDataSource(
    private val apiServices: ApiServices,
) {
    fun getPokemonList() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 25),
        pagingSourceFactory = {
            PagingDataSource(apiServices)
        }
    ).flow

    fun getPokemonInfo(pokemonName: String): Flow<ApiResponse<Pokemon>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiServices.getPokemonInfo(pokemonName)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }
}