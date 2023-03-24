package com.sulistyo.mypokemon.core.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_pokemon")
@Parcelize
data class PokemonListEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pokemonName: String,
    val nickName: String? = null,
    val imageUrl: String,
    val number: Int,
    val changeNumber: Int = 0,
) : Parcelable
