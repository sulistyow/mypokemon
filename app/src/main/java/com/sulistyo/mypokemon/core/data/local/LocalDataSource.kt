package com.sulistyo.mypokemon.core.data.local

import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: PokemonDao) {

    fun getMyPokemons(): Flow<List<PokemonListEntry>> = dao.getMyPokemons()

    fun insertPokemon(data: PokemonListEntry) = dao.insertPokemon(data)

    fun deletePokemon(data: PokemonListEntry) = dao.deletePokemon(data)
}