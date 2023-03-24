package com.sulistyo.mypokemon.presentation.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sulistyo.mypokemon.databinding.FragmentPokemonListBinding
import com.sulistyo.mypokemon.presentation.adapter.PokemonListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class PokemonListFragment : Fragment() {

    lateinit var bind: FragmentPokemonListBinding

    private val viewModel: PokemonListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentPokemonListBinding.inflate(inflater, container, false)
        return bind.root
    }

    private val mAdapter = PokemonListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayout = GridLayoutManager(requireContext(), 2)

        bind.rvPokemonList.apply {
            layoutManager = gridLayout
            adapter = mAdapter
        }

        viewModel.pokemonList.observe(viewLifecycleOwner) { response ->
            mAdapter.submitData(lifecycle, response)
        }

    }

}

