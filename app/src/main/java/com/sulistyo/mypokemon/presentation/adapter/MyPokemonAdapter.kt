package com.sulistyo.mypokemon.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import com.sulistyo.mypokemon.databinding.ItemMyPokemonBinding
import com.sulistyo.mypokemon.presentation.mypokemon.MyPokemonDetailActivity
import com.sulistyo.mypokemon.utils.Actions

class MyPokemonAdapter(private val action: (Actions, PokemonListEntry) -> Unit) :
    RecyclerView.Adapter<MyPokemonAdapter.ViewHolder>() {
    private var onClickedAt = 0L
    private var mData = ArrayList<PokemonListEntry>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<PokemonListEntry>?) {
        if (newListData == null) return
        mData.clear()
        mData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mData[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMyPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mData.size


    inner class ViewHolder(
        private val bind: ItemMyPokemonBinding
    ) : RecyclerView.ViewHolder(bind.root) {

        fun bind(pokemon: PokemonListEntry) {
            bind.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != NO_POSITION }
                    ?: return@setOnClickListener
                val currentClickedAt = SystemClock.elapsedRealtime()
                if (currentClickedAt - onClickedAt > bind.transformationLayout.duration) {
                    MyPokemonDetailActivity.startActivity(
                        bind.transformationLayout,
                        mData[position]
                    )
                    onClickedAt = currentClickedAt
                }
            }

            bind.tvPokemonName.text = pokemon.pokemonName
            bind.tvPokemonNickname.text = pokemon.nickName

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

            bind.btEdit.setOnClickListener {
                action.invoke(Actions.EDIT, pokemon)
            }
            bind.btDelete.setOnClickListener {
                action.invoke(Actions.DELETE, pokemon)
            }
        }

    }

}