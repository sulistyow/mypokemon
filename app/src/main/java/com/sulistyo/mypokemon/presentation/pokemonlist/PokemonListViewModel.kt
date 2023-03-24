package com.sulistyo.mypokemon.presentation.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sulistyo.mypokemon.core.domain.usecase.IPokemonUseCases


class PokemonListViewModel(
    useCase: IPokemonUseCases
) : ViewModel() {

    val pokemonList = useCase.getPokemonList().asLiveData().cachedIn(viewModelScope)

    /* private var currentResult: Flow<PagingData<PokemonListEntry>>? = null
     fun getPokemonList(): Flow<PagingData<PokemonListEntry>> {
         val newResult: Flow<PagingData<PokemonListEntry>> =
             repo.getPokemonList().cachedIn(viewModelScope)
         currentResult = newResult
         return newResult
     }*/

    /* mapIndexed { index, entry ->
         val number = if (entry.url.endsWith("/")) {
             entry.url.dropLast(1).takeLastWhile { it.isDigit() }
         } else {
             entry.url.takeLastWhile { it.isDigit() }
         }
         var url =
             "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
         PokemonListEntry(
             entry.name.capitalize(Locale.ROOT), url, number.toInt()
         )*/

}