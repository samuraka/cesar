package com.alvloureiro.popcorn.injection.components

import com.alvloureiro.popcorn.PopcornApplication
import com.alvloureiro.popcorn.injection.modules.AppModule
import com.alvloureiro.popcorn.injection.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(app: PopcornApplication)
    fun plus(module: NetworkModule): NetworkComponent
}
