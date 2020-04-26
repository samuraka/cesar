package com.alvloureiro.popcorn.injection.modules

import com.alvloureiro.popcorn.PopcornApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module class AppModule(val app: PopcornApplication) {
    @Provides
    @Singleton
    fun providesApplication() = app
}
