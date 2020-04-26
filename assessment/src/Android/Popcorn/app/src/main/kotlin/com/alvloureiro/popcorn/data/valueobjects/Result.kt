package com.alvloureiro.popcorn.data.valueobjects

import com.google.gson.annotations.Expose


data class Result(
        @Expose
        var page: Int? = null,

        @Expose
        var total_results: Int? = null,

        @Expose
        var total_pages: Int? = null,

        @Expose
        var results: ArrayList<Movie>? = null

): VO
