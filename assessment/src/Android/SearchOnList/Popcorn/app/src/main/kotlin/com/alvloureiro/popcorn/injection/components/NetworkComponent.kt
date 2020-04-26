package com.alvloureiro.popcorn.injection.components

import com.alvloureiro.popcorn.data.model.TMDBDataModel
import com.alvloureiro.popcorn.injection.modules.NetworkModule
import com.alvloureiro.popcorn.ui.MainActivity
import com.google.gson.Gson
import dagger.Subcomponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton @Subcomponent(modules = [(NetworkModule::class)])
interface NetworkComponent {
    fun inject(mainActivity: MainActivity)

    fun retrofit(): Retrofit
    fun okhttp(): OkHttpClient
    fun gson(): Gson
    fun cache(): Cache

    fun movieDataModel(): TMDBDataModel
}
