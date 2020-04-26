package com.alvloureiro.popcorn.api

import com.alvloureiro.popcorn.data.valueobjects.GenresResult
import com.alvloureiro.popcorn.data.valueobjects.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesDataBase {
    companion object {
        const val BASE_ENDPOINT = "/3"
        const val GET_UPCOMING = "/movie/upcoming"
        const val GET_GENRE_LIST = "/genre/movie/list"
        const val GET_SEARCH_MOVIE = "/search/movie"
    }

    @GET(BASE_ENDPOINT + GET_UPCOMING)
    fun upcomingMovies(@Query("page") page: Int): Observable<Result>?

    @GET(BASE_ENDPOINT + GET_GENRE_LIST)
    fun genres(): Observable<GenresResult>?

    @GET(BASE_ENDPOINT + GET_SEARCH_MOVIE)
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Observable<Result>?
}
