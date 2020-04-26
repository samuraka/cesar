package com.alvloureiro.popcorn

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.alvloureiro.popcorn.injection.components.AppComponent
import com.alvloureiro.popcorn.injection.components.DaggerAppComponent
import com.alvloureiro.popcorn.injection.modules.AppModule
import com.alvloureiro.popcorn.network.AppNetworkManager


class PopcornApplication: Application() {

    private companion object {
        const val PREF_FILE_NAME = "com.alvloureiro.popcorn.preferences"
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    val networkState: AppNetworkManager by lazy {
        AppNetworkManager(baseContext)
    }

    val preferences: SharedPreferences by lazy {
        baseContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}
