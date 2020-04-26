package com.alvloureiro.popcorn.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import com.alvloureiro.popcorn.BuildConfig
import com.alvloureiro.popcorn.PopcornApplication
import com.alvloureiro.popcorn.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


val AppCompatActivity.app: PopcornApplication get() = application as PopcornApplication

val Context.app: PopcornApplication get() = applicationContext as PopcornApplication

fun AppCompatActivity.hideSoftKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

inline fun <reified T : Any> AppCompatActivity.launchActivity (
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()

    startActivity(intent)
}

fun View.show() {
    context?.let {
        startAnimation(AnimationUtils.loadAnimation(it, R.anim.fade_in))
    }

    if (visibility == View.GONE)
        visibility = View.VISIBLE
}

fun View.hide() {
    context?.let {
        startAnimation(AnimationUtils.loadAnimation(it, R.anim.fade_out))
    }

    if (visibility == View.VISIBLE)
        visibility = View.GONE
}

fun String.toUri(): Uri {
    return Uri.parse(this)
}

fun String.toDisplayDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val releaseDate = dateFormat.parse(this)
    val displayReleaseDate = SimpleDateFormat("EEE, MMM d, ''yy")
    return displayReleaseDate.format(releaseDate)
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)

}

fun AppCompatImageView.loadPosterFromUrl(url: String?) {
    if (url != null) {
        val posterUrl = "${BuildConfig.BASE_IMG_URL}/w154$url"
        Picasso.with(context).load(posterUrl.toUri()).into(this)
    } else {
        Picasso.with(context).load(R.drawable.ic_movie_poster_placeholder).into(this)
    }
}

fun AppCompatImageView.loadBackdropImageFromUrl(url: String) {
    val backImgUrl = "${BuildConfig.BASE_IMG_URL}/w780$url"
    Picasso.with(context).load(backImgUrl.toUri()).into(this)
}

fun Float.convertVoteAverageToRating(): Float {
    return (5 * this) / 10
}

inline fun <reified T : Any> newIntent(context: Context): Intent
        = Intent(context, T::class.java)
