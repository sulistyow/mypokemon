package com.sulistyo.mypokemon.core.data.remote

import com.sulistyo.mypokemon.core.data.remote.responses.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon
}