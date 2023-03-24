package com.sulistyo.mypokemon.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import java.io.IOException
import java.util.*

class PagingDataSource(private val api: ApiServices) :
    PagingSource<Int, PokemonListEntry>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListEntry> {
        val offset = params.key ?: 0

        return try {
            val responseData = api.getPokemonList(params.loadSize, offset)

            val lastResult = responseData.results.mapIndexed { index, entry ->
                val number = if (entry.url.endsWith("/")) {
                    entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                } else {
                    entry.url.takeLastWhile { it.isDigit() }
                }
                var url =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png"
                PokemonListEntry(
                    pokemonName = entry.name.capitalize(Locale.ROOT),
                    imageUrl = url,
                    number = number.toInt()
                )
            }
            LoadResult.Page(
                data = lastResult,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (responseData.next == null) null else offset + params.loadSize
            )
        } catch (t: Throwable) {
            var exception = t

            if (t is IOException) {
                exception = IOException("Please check internet connection")
            }
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, PokemonListEntry>): Int? {
        return state.anchorPosition
    }

}