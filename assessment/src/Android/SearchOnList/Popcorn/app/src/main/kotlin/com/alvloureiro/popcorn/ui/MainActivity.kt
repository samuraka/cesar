package com.alvloureiro.popcorn.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.alvloureiro.popcorn.R
import com.alvloureiro.popcorn.data.valueobjects.*
import com.alvloureiro.popcorn.data.valueobjects.AppStateType.*
import com.alvloureiro.popcorn.extensions.app
import com.alvloureiro.popcorn.extensions.hide
import com.alvloureiro.popcorn.extensions.launchActivity
import com.alvloureiro.popcorn.extensions.show
import com.alvloureiro.popcorn.injection.components.NetworkComponent
import com.alvloureiro.popcorn.injection.modules.NetworkModule
import com.alvloureiro.popcorn.ui.adapter.MovieListViewAdapter
import com.alvloureiro.popcorn.ui.viewmodel.MovieDBViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    companion object {
        const val UPCOMING_MOVIES = "UPCOMING_MOVIES"
        const val MOVIE_MODEL = "MODEL"
        const val IS_GENRES_FETCHED = "IS_GENRES_FETCHED"
        const val CURRENT_BOOK_LIST_PAGE = "CURRENT_BOOK_LIST_PAGE"
        const val BOOK_LIST_MAX_PAGES = "BOOK_LIST_MAX_PAGES"
    }

    private val component: NetworkComponent by lazy {
        app.component.plus(NetworkModule(this))
    }

    private val mLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val mScrollListener: ScrollListener by lazy {
        ScrollListener()
    }

    private val mMovieAdapter = MovieListViewAdapter {
        launchActivity<MovieDetailActivity> {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(MOVIE_MODEL, it)
        }

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private val mSubscription: CompositeDisposable = CompositeDisposable()

    private var mUpcomingMovies: Result? = null

    private var mLoadingScrolling = true

    private var mCurrentPage: Int = 1

    private var mMaxPages: Int = 0

    @Inject lateinit var mViewModel: MovieDBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        component.inject(this)

        movieListView?.adapter = mMovieAdapter
        movieListView?.addOnScrollListener(mScrollListener)

        progressBar.show()

        restoreInstanceState(savedInstanceState)

        if (!app.preferences.contains(IS_GENRES_FETCHED)) {
            mViewModel.movieGenres()
        }

        movieListView?.layoutManager = mLayoutManager

        btnRefetch?.setOnClickListener {
            progressBar.show()

            if (!app.preferences.contains(IS_GENRES_FETCHED)) {
                mViewModel.movieGenres()
            }

            mViewModel.upcomingMovies(mCurrentPage)

            it.hide()
        }

        if (mUpcomingMovies == null) {
            mViewModel.upcomingMovies(mCurrentPage)
        }
    }

    override fun onResume() {
        super.onResume()
        mSubscription.add(mViewModel.viewModelStates
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {state -> this.handleAppState(state)}))
    }

    override fun onPause() {
        mSubscription.clear()
        mViewModel.clearDisposables()

        super.onPause()
    }

    override fun onDestroy() {
        movieListView?.removeOnScrollListener(mScrollListener)
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_BOOK_LIST_PAGE, mCurrentPage)

        outState.putInt(BOOK_LIST_MAX_PAGES, mMaxPages)

        mMovieAdapter.getMovies()?.let {
            if (it.isNotEmpty()) {
                outState.putSerializable(UPCOMING_MOVIES, mUpcomingMovies)
            }
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreInstanceState(savedInstanceState)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { state ->

            if (state.containsKey(CURRENT_BOOK_LIST_PAGE)) {
                mCurrentPage = state[CURRENT_BOOK_LIST_PAGE] as Int
            }

            if (state.containsKey(BOOK_LIST_MAX_PAGES)) {
                mMaxPages = state[BOOK_LIST_MAX_PAGES] as Int
            }

            if (state.containsKey(UPCOMING_MOVIES)) {
                mUpcomingMovies = state[UPCOMING_MOVIES] as Result?
                mUpcomingMovies?.let { result ->
                    result.results?.let { list -> mMovieAdapter.setMovies(list) }
                }
                progressBar.hide()
            }
        }
    }

    private fun handleAppState(state: AppState<VO>) {
        when(state.type) {
            APP_LOADING -> {
                progressBar?.show()
            }
            APP_DID_FETCH_GENRES_SUCCESS -> {
                state.payload?.let {
                    val result = it as GenresResult
                    result.genres?.forEach{ genre ->
                        app.preferences.edit().putString(genre.id?.toString(), genre.name).apply()
                    }

                    app.preferences.edit().putBoolean(IS_GENRES_FETCHED, true).apply()
                }
            }
            APP_DID_FETCH_MOVIES_SUCCESS -> {
                progressBar?.hide()

                state.payload?.let {
                    mUpcomingMovies = it as Result
                    mMaxPages = mUpcomingMovies?.total_pages as Int

                    mMovieAdapter.addMovies(mUpcomingMovies?.results as ArrayList<Movie>)
                    if (!mLoadingScrolling) {
                        mLoadingScrolling = true
                    }
                }
            }
            APP_DID_FETCH_MOVIES_FAIL -> {
                progressBar?.hide()
                btnRefetch?.show()
                Toast.makeText(
                        this,
                        getString(R.string.label_toast_did_fetch_movies_fail_text),
                        Toast.LENGTH_LONG
                ).show()
            }
            APP_DID_FETCH_SEARCH_SUCCESS -> {
                progressBar?.hide()
                state.payload?.let {
                    val searchResult = it as Result;
                    if (searchResult.results?.isNotEmpty()!!) {
                        mUpcomingMovies = null
                        mUpcomingMovies = searchResult
                        mMovieAdapter.setMovies(ArrayList())
                        mMaxPages = mUpcomingMovies?.total_pages as Int

                        mMovieAdapter.addMovies(mUpcomingMovies?.results as ArrayList<Movie>)
                        if (!mLoadingScrolling) {
                            mLoadingScrolling = true
                        }
                    }

                }
            }
            APP_DID_FETCH_SEARCH_FAIL -> {
                progressBar?.hide()
                btnRefetch?.show()
                Toast.makeText(
                        this,
                        getString(R.string.label_toast_did_fetch_movies_fail_text),
                        Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
    }

    inner class ScrollListener: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

            if (dy > 0) {

                val visibleItems = mLayoutManager.childCount
                val totalItems = mLayoutManager.itemCount
                val pastItems = mLayoutManager.findFirstVisibleItemPosition()

                if(mLoadingScrolling) {
                    if ((visibleItems + pastItems) >= totalItems) {
                        mLoadingScrolling = false

                        if (mCurrentPage < mMaxPages) {
                            mCurrentPage++
                            mViewModel.upcomingMovies(mCurrentPage)
                        } else {
                            Toast.makeText(
                                    recyclerView?.context,
                                    recyclerView?.context?.getString(R.string.label_toast_reach_end_list_text),
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.appmenu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                progressBar.show()
                mViewModel.search(query, 1)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}
