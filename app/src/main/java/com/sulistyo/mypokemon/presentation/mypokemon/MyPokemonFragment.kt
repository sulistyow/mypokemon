package com.sulistyo.mypokemon.presentation.mypokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import com.sulistyo.mypokemon.databinding.FragmentMyPokemonBinding
import com.sulistyo.mypokemon.presentation.adapter.MyPokemonAdapter
import com.sulistyo.mypokemon.utils.*
import com.sulistyo.mypokemon.utils.DialogUtils.showConfirmDialog
import com.sulistyo.mypokemon.utils.DialogUtils.showFailedReleaseDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPokemonFragment : Fragment() {

    private lateinit var bind: FragmentMyPokemonBinding
    private val viewModel: MyPokemonViewModel by viewModel()

    private val mAdapter = MyPokemonAdapter { action, pokemon ->
        when (action) {
            Actions.EDIT -> {
                var changeNumber = pokemon.changeNumber
                val newName: String = if (pokemon.nickName?.takeLast(2)!!.contains("-")) {
                    changeNumber = pokemon.changeNumber + 1
                    pokemon.nickName.dropLast(1) + fibonacciSeries(changeNumber)
                } else {
                    changeNumber = (changeNumber + 1)
                    "${pokemon.nickName}-${fibonacciSeries(1)}"
                }

                CustomDialogFragment(pokemon.imageUrl, true, newName) { nickname ->
                    val poke = PokemonListEntry(
                        id = pokemon.id,
                        nickName = nickname,
                        pokemonName = pokemon.pokemonName,
                        number = pokemon.number,
                        imageUrl = pokemon.imageUrl,
                        changeNumber = changeNumber
                    )
                    viewModel.insertPokemon(poke)
                    Toast.makeText(context, "$nickname Saved", Toast.LENGTH_LONG)
                        .show()
                }.show(requireActivity().supportFragmentManager, "Edit")
            }
            Actions.DELETE -> {
                activity?.showConfirmDialog(
                    "Release Pokemon",
                    "Do you want to Release ${pokemon.nickName}"
                ) { _, _ ->
                    if (primeNumber()) {
                        releasePokemon(pokemon)
                    } else {
                        requireActivity().showFailedReleaseDialog()
                    }
                }
            }
        }
    }

    private fun releasePokemon(pokemon: PokemonListEntry) {
        viewModel.deletePokemon(pokemon)
        Toast.makeText(context, "${pokemon.nickName} Released", Toast.LENGTH_LONG)
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentMyPokemonBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.myPokemons.observe(viewLifecycleOwner) { result ->
            if (result.isNotEmpty()) {
                bind.rvMypokemon.visible()
                bind.viewEmpty.root.gone()
                initView(result)
            } else {
                bind.rvMypokemon.gone()
                bind.viewEmpty.root.visible()
            }
        }
    }

    private fun initView(result: List<PokemonListEntry>?) {
        mAdapter.setData(result)
        bind.rvMypokemon.apply {
            adapter = mAdapter
        }
    }

}