package com.alvloureiro.popcorn.data.valueobjects

import com.google.gson.annotations.Expose


data class GenresResult(
        @Expose
        var genres: List<Genre>? = null
): VO
