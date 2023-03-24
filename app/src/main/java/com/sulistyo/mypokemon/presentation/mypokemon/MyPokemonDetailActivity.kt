package com.sulistyo.mypokemon.presentation.mypokemon

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.skydoves.androidribbon.ribbonView
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.sulistyo.mypokemon.R
import com.sulistyo.mypokemon.core.data.local.entity.PokemonListEntry
import com.sulistyo.mypokemon.core.data.remote.responses.ApiResponse
import com.sulistyo.mypokemon.core.data.remote.responses.Pokemon
import com.sulistyo.mypokemon.databinding.ActivityMyPokemonDetailBinding
import com.sulistyo.mypokemon.presentation.pokemondetail.PokemonDetailActivity
import com.sulistyo.mypokemon.presentation.pokemondetail.PokemonDetailViewModel
import com.sulistyo.mypokemon.utils.CustomProgressDialog
import com.sulistyo.mypokemon.utils.PokemonTypeUtils
import com.sulistyo.mypokemon.utils.SpacesItemDecoration
import com.sulistyo.mypokemon.utils.parseStatToAbbr
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MyPokemonDetailActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMyPokemonDetailBinding

    private val viewModel: PokemonDetailViewModel by viewModel()

    private val pokemon: PokemonListEntry by bundleNonNull(PokemonDetailActivity.EXTRA_POKEMON)

    private val customDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMyPokemonDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.getPokemonInfo(pokemon.pokemonName.toLowerCase()).observe(this) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    bind.progressbar.visibility = View.GONE
                    initView(response.data)
                }
                is ApiResponse.Error -> {
                    Toast.makeText(this, response.errorMessage, Toast.LENGTH_LONG).show()
                }
                is ApiResponse.Loading -> {

                }
            }
        }
    }

    private fun initView(poke: Pokemon) {
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
                            val dominantColor = it.dominantSwatch?.rgb ?: 0
                            bind.header.setBackgroundColor(dominantColor)
                            window.apply {
                                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                                statusBarColor = dominantColor
                            }
                        }
                    }
                    return false
                }
            }).into(bind.image)

        val stats: HashMap<String, Float> = hashMapOf()

        for (i in poke.stats.indices) {
            val stat = poke.stats[i]
            stats[parseStatToAbbr(stat)] = stat.baseStat.toFloat()
        }

        val hp = stats["HP"]!!
        val atk = stats["Atk"]!!
        val def = stats["Def"]!!
        val spd = stats["Spd"]!!

        for (type in poke.types) {
            with(bind.ribbonRecyclerView) {
                addRibbon(
                    ribbonView(this@MyPokemonDetailActivity) {
                        setText(type.type.name)
                        setTextColor(Color.WHITE)
                        setPaddingLeft(84f)
                        setPaddingRight(84f)
                        setPaddingTop(2f)
                        setPaddingBottom(10f)
                        setTextSize(16f)
                        setRibbonRadius(120f)
                        setTextStyle(Typeface.BOLD)
                        setRibbonBackgroundColorResource(
                            PokemonTypeUtils.getTypeColor(type.type.name)
                        )
                    }.apply {
                        maxLines = 1
                        gravity = Gravity.CENTER
                    }
                )
                addItemDecoration(SpacesItemDecoration())

            }
        }

        for (move in poke.moves) {
            with(bind.movesRibbonRecyclerView) {
                addRibbon(
                    ribbonView(this@MyPokemonDetailActivity) {
                        setText(move.move.name)
                        setTextColor(Color.WHITE)
                        setPaddingLeft(84f)
                        setPaddingRight(84f)
                        setPaddingTop(2f)
                        setPaddingBottom(10f)
                        setTextSize(16f)
                        setRibbonRadius(120f)
                        setTextStyle(Typeface.BOLD)
                        setRibbonBackgroundColorResource(
                            R.color.gray_21
                        )
                    }.apply {
                        maxLines = 1
                        gravity = Gravity.CENTER
                    }
                )

            }
        }

        with(bind) {
            nickname.text = pokemon.nickName
            name.text = poke.name.capitalize(Locale.ROOT)
            height.text = poke.getHeightString()
            weight.text = poke.getWeightString()

            progressHp.apply {
                min = 0F
                max = 100F
                progress = hp
                labelText = hp.toString()
                autoAnimate = true
            }

            progressAtk.apply {
                min = 0F
                max = 100F
                progress = atk
                labelText = atk.toString()
                autoAnimate = true
            }

            progressDef.apply {
                min = 0F
                max = 100F
                progress = def
                labelText = def.toString()
                autoAnimate = true
            }

            progressSpd.apply {
                min = 0F
                max = 100F
                progress = spd
                labelText = spd.toString()
                autoAnimate = true
            }

            arrow.setOnClickListener {
                finish()
            }

        }
    }

    companion object {
        @VisibleForTesting
        internal const val EXTRA_POKEMON = "EXTRA_POKEMON"

        fun startActivity(transformationLayout: TransformationLayout, pokemon: PokemonListEntry) =
            transformationLayout.context.intentOf<MyPokemonDetailActivity> {
                putExtra(EXTRA_POKEMON to pokemon)
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}