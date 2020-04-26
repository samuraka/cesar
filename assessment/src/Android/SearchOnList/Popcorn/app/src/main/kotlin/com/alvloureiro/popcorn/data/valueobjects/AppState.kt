package com.alvloureiro.popcorn.data.valueobjects

import com.alvloureiro.popcorn.data.valueobjects.AppStateType.*


data class AppState<T>(
        var type: AppStateType = APP_UNKNOWN,
        var payload: T? = null
)
