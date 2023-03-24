package com.sulistyo.mypokemon.core.data.remote.responses


import com.google.gson.annotations.SerializedName

data class PokemonResult(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)