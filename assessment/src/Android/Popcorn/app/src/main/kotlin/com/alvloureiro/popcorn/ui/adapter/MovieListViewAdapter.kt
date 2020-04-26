package com.alvloureiro.popcorn.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.alvloureiro.popcorn.R
import com.alvloureiro.popcorn.data.valueobjects.Movie
import com.alvloureiro.popcorn.extensions.*
import kotlinx.android.synthetic.main.moviecard_item.view.*


class MovieListViewAdapter
    : RecyclerView.Adapter<MovieListViewAdapter.MViewHolder> {

    companion object {
        const val TAG = "MovieListAdapter"
    }

    private var mMovies: ArrayList<Movie>? = arrayListOf()
    private val mListener: (Movie) -> Unit

    constructor(listener: (Movie) -> Unit) : super() {
        mListener = listener
    }

    class MViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: Movie?, listener: (Movie) -> Unit) = with(itemView){
            moviePoster.loadPosterFromUrl(url = item?.poster_path)

            movieName.text = context?.getString(R.string.movie_title_name_text, item?.title)
            if (item?.release_date?.isNotEmpty()!!) {
                movieReleaseDate.text = context?.getString(
                        R.string.movie_release_date_text,
                        item?.release_date?.toDisplayDate()
                )
            }

            ratingBar.rating = item?.vote_average?.convertVoteAverageToRating()!!

            val genres = item.genre_ids?.map { it.toString() }?.map {
                context?.app?.preferences?.getString(it, "")
            }

            if (genres?.isNotEmpty() as Boolean) {
                movieGenres.text = context?.getString(
                        R.string.label_movie_genres_text,
                        genres.reduce{ displayGenres, genre -> displayGenres?.plus(genre)?.plus("\n")}
                )
            } else {
                movieGenres.text = context.getString(R.string.label_movie_genres_text, "")
            }

            setOnClickListener{
                listener(item)
            }
        }
    }

    fun addMovies(movies: List<Movie>) {
        var initPosition = 0
        if (mMovies?.size as Int > 0) {
            initPosition = mMovies?.size as Int
        }

        mMovies?.addAll(initPosition, movies)
        notifyItemRangeChanged(initPosition, mMovies?.size?.plus(1) as Int)
    }

    fun setMovies(movies: ArrayList<Movie>) {
        mMovies = movies
    }

    fun getMovies() = mMovies

    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {
        holder?.bind(mMovies?.get(position), mListener)
    }

    override fun getItemCount(): Int = mMovies?.size!!

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder {
        return MViewHolder(parent?.inflate(R.layout.moviecard_item)!!)
    }
}
