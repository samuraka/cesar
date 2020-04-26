package com.alvloureiro.popcorn.ui.viewmodel

import com.alvloureiro.popcorn.data.model.TMDBDataModel
import com.alvloureiro.popcorn.data.valueobjects.AppState
import com.alvloureiro.popcorn.data.valueobjects.AppStateType.*
import com.alvloureiro.popcorn.data.valueobjects.VO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class MovieDBViewModel @Inject constructor(private val movieDataDataModel: TMDBDataModel) {
    companion object {
        const val TAG = "MovieDBViewModel"
    }

    val viewModelStates: PublishSubject<AppState<VO>> = PublishSubject.create()

    private val mDisposable: CompositeDisposable = CompositeDisposable()

    fun upcomingMovies(page: Int) {
        mDisposable.addAll(movieDataDataModel.upComingMovies(page)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                        {
                            result -> viewModelStates.onNext(
                                                        AppState(
                                                                APP_DID_FETCH_MOVIES_SUCCESS,
                                                                result
                                                        )
                        )
                        },
                        {
                            _ -> viewModelStates.onNext(AppState(APP_DID_FETCH_MOVIES_FAIL))
                        }))
    }

    fun movieGenres() {
        mDisposable.addAll(movieDataDataModel.genres()
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                        {
                            genres -> viewModelStates.onNext(
                                                AppState(
                                                        APP_DID_FETCH_GENRES_SUCCESS,
                                                        genres
                                                )
                            )
                        },
                        {
                            _ -> viewModelStates.onNext(AppState(APP_DID_FETCH_GENRES_FAIL))
                        }
                ))
    }

    fun search(title: String, page: Int) {
        mDisposable.addAll(movieDataDataModel.searchMovie(title, page)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                        {
                            movies -> viewModelStates.onNext(
                                AppState(
                                        APP_DID_FETCH_SEARCH_SUCCESS,
                                        movies
                                )
                            )
                        },
                        {
                            _ -> viewModelStates.onNext(AppState(APP_DID_FETCH_SEARCH_FAIL))
                        }
                ))

    }

    fun clearDisposables() {
        mDisposable.clear()
    }
}
