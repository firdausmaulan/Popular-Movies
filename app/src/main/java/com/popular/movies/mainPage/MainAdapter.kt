package com.popular.movies.mainPage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popular.movies.R
import com.popular.movies.model.ModelMovies
import com.popular.movies.util.loadImage
import kotlinx.android.synthetic.main.adapter_main.view.*

class MainAdapter(val context: Context?,
                  private val mMovieList: ArrayList<ModelMovies.Result>)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var clickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        context?.loadImage(holder.ivMovie, mMovieList[position].posterPath)
        holder.tvTitle?.text = mMovieList[position].title
        holder.tvTitle?.isSelected = true
        holder.tvGenre?.text = mMovieList[position].genres.toString()
        mMovieList[position].isFavourite?.let { isFavourite ->
            if (isFavourite) {
                holder.ivFavourite?.setImageResource(R.drawable.ic_favorite)
            } else {
                holder.ivFavourite?.setImageResource(R.drawable.ic_unfavorite)
            }
        }
        holder.ivFavourite?.setOnClickListener {
            clickListener?.onFavouriteClick(position, holder.ivFavourite, mMovieList[position])
        }
        holder.lytParent?.setOnClickListener {
            holder.ivMovie?.let { imageView ->
                clickListener?.onItemClick(position, imageView, mMovieList[position])
            }
        }
    }

    override fun getItemCount(): Int = mMovieList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMovie = itemView.ivMovie
        val tvTitle = itemView.tvTitle
        val ivFavourite = itemView.ivFavourite
        val tvGenre = itemView.tvGenre
        val lytParent = itemView.lytParent
    }

    fun setOnItemClickListener(mClickListener: ClickListener) {
        clickListener = mClickListener
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View, model: ModelMovies.Result?)
        fun onFavouriteClick(position: Int, v: View, model: ModelMovies.Result?)
    }
}