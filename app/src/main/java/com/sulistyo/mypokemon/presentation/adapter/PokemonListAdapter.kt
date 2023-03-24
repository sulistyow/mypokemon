package com.sulistyo.mypokemon.presentation.adapter

import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import com.sulistyo.mypokemon.databinding.ItemPokemonBinding
import com.sulistyo.mypokemon.presentation.pokemondetail.PokemonDetailActivity


class PokemonListAdapter :
    PagingDataAdapter<PokemonListEntry, PokemonListAdapter.ViewHolder>(
        PokemonDiffCallback()
    ) {
    private var onClickedAt = 0L

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)!!
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(
        private val bind: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(bind.root) {

        fun bind(pokemon: PokemonListEntry) {
            bind.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != NO_POSITION }
                    ?: return@setOnClickListener
                val currentClickedAt = SystemClock.elapsedRealtime()
                if (currentClickedAt - onClickedAt > bind.transformationLayout.duration) {
                    PokemonDetailActivity.startActivity(
                        bind.transformationLayout,
                        getItem(position)!!
                    )
                    onClickedAt = currentClickedAt
                }
            }

            bind.tvPokemonName.text = pokemon.pokemonName

            Glide.with(bind.root)
                .load(pokemon.imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("ERROR", "Failed Load")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Palette.from(resource!!.toBitmap()).generate { palette ->
                            palette?.let {
                                val intColor = it.dominantSwatch?.rgb ?: 0
                                bind.holder.setBackgroundColor(intColor)
                            }
                        }
                        return false
                    }
                }).into(bind.ivPokemon)
        }

    }

    private class PokemonDiffCallback : DiffUtil.ItemCallback<PokemonListEntry>() {
        override fun areItemsTheSame(
            oldItem: PokemonListEntry,
            newItem: PokemonListEntry
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: PokemonListEntry,
            newItem: PokemonListEntry
        ): Boolean {
            return oldItem == newItem
        }

    }
}