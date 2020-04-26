package com.alvloureiro.popcorn.data.valueobjects

import com.google.gson.annotations.Expose


data class Movie(
        @Expose
        var vote_count: Int? = null,

        @Expose
        var id: Int? = null,

        @Expose
        var video: Boolean? = null,

        @Expose
        var vote_average: Float? = null,

        @Expose
        var title: String? = null,

        @Expose
        var popularity: Float? = null,

        @Expose
        var poster_path: String? = null,

        @Expose
        var original_language: String? = null,

        @Expose
        var original_title: String? = null,

        @Expose
        var genre_ids: ArrayList<Int>? = null,

        @Expose
        var backdrop_path: String? = null,

        @Expose
        var adult: Boolean? = null,

        @Expose
        var overview: String? = null,

        @Expose
        var release_date: String? = null

): VO
