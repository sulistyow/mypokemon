package com.sulistyo.mypokemon.core.data.local

import androidx.room.*
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM tb_pokemon")
    fun getMyPokemons(): Flow<List<PokemonListEntry>>

    @Upsert
    fun insertPokemon(data: PokemonListEntry)

    @Delete
    fun deletePokemon(data: PokemonListEntry)
}