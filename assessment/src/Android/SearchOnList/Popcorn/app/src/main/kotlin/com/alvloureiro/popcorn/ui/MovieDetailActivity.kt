package com.alvloureiro.popcorn.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import com.alvloureiro.popcorn.R
import com.alvloureiro.popcorn.data.valueobjects.Movie
import com.alvloureiro.popcorn.extensions.convertVoteAverageToRating
import com.alvloureiro.popcorn.extensions.loadBackdropImageFromUrl
import com.alvloureiro.popcorn.extensions.toDisplayDate
import kotlinx.android.synthetic.main.movie_item_detail.*


class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MovieDetailActivity"
        const val MOVIE_MODEL = "MODEL"
    }

    private val mMovie: Movie by lazy {
        intent?.extras?.get(MOVIE_MODEL) as Movie
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_item_detail)

        movieSinopse?.movementMethod = ScrollingMovementMethod()

        initViewFromModel()
    }

    private fun initViewFromModel() {

        mMovie.backdrop_path?.let { backdropImg?.loadBackdropImageFromUrl(it) }
        movieName.text = getString(R.string.movie_title_name_text, mMovie.title)
        premiereDate.text = getString(R.string.movie_release_date_text, mMovie.release_date?.toDisplayDate())
        movieSinopse.text = getString(R.string.movie_sinopse_text, mMovie.overview)
        ratingBar.rating = mMovie.vote_average?.convertVoteAverageToRating() as Float
    }
}
