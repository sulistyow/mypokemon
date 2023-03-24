package com.sulistyo.mypokemon.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry

@Database(
    entities = [PokemonListEntry::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}