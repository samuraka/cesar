package com.alvloureiro.popcorn.data.model

import com.alvloureiro.popcorn.api.MoviesDataBase
import com.alvloureiro.popcorn.data.valueobjects.GenresResult
import com.alvloureiro.popcorn.data.valueobjects.Result
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TMDBDataModel @Inject constructor(private val moviesDataBase: MoviesDataBase){

    fun upComingMovies(page: Int = 1): Observable<Result>? = moviesDataBase.upcomingMovies(page)

    fun genres(): Observable<GenresResult>? {
        return moviesDataBase.genres()
    }


    fun searchMovie(movieTitle: String, page: Int): Observable<Result>? =
        moviesDataBase.searchMovie(movieTitle, page)
}
