# Popcorn
Simple proof of concept that access the [The Movie Database API](https://www.themoviedb.org/). The goal of this project is make a
simple application that uses the most common Android libraries with Kotlin.
Currently we only show a list with the upcoming movies containing a poster image, title, rating, genres
and the movie's release date, so when the user clicks on an item list it will navigate to a detail view
and then the user can read a movie's overview text.



### Tech Stack
The technical stack used in this project consists in:

* MVVM: Model View ViewModel architecture
* Kotlin: As main project language
* Kotlin Android Extensions: to access layout fields easily
* Dagger2: Dependency injection
* Retrofit: Rest API calls
* Picasso: Remote image loading
* OkHttp3: Network client and Caching
* Gson: JSON parser
* RxKotlin: Rx programming

### Workflow

#### Commit messages
All the commit messages should use the imperative mood in description, first you should use a single line commit
if necessary you can explain the commit's motivation always using the `what` and `why`.

##### Message structure
The commit message consists of the three distinct parts separated by a blank line: the title,
an optional body and the author's signature. It looks like below:
```
    [TagName]: Short description

    Body of explanation, explaning the motivation use the rule `what` and `why`, not the how.

    <Author-Signature>
```

### Project Package Structure

The below structure are based on the following base package id: `com.alvloureiro.popcorn`
```bash
├── AndroidManifest.xml
├── kotlin
│   └── com
│       └── alvloureiro
│           └── popcorn
│               ├── api
│               │   └── MoviesDataBase.kt
│               ├── data
│               │   ├── model
│               │   │   └── TMDBDataModel.kt
│               │   └── valueobjects
│               │       ├── AppState.kt
│               │       ├── AppStateType.kt
│               │       ├── Genre.kt
│               │       ├── GenresResult.kt
│               │       ├── Movie.kt
│               │       ├── Result.kt
│               │       └── VO.kt
│               ├── extensions
│               │   └── Extensions.kt
│               ├── injection
│               │   ├── components
│               │   │   ├── AppComponent.kt
│               │   │   └── NetworkComponent.kt
│               │   └── modules
│               │       ├── AppModule.kt
│               │       └── NetworkModule.kt
│               ├── network
│               │   ├── AppNetworkManager.kt
│               │   └── interceptors
│               │       ├── HttpCacheInterceptor.kt
│               │       ├── HttpOfflineCacheInterceptor.kt
│               │       └── HttpQueryInterceptor.kt
│               ├── PopcornApplication.kt
│               └── ui
│                   ├── adapter
│                   │   └── MovieListViewAdapter.kt
│                   ├── MainActivity.kt
│                   ├── MovieDetailActivity.kt
│                   └── viewmodel
│                       └── MovieDBViewModel.kt
└── res
    ├── anim
    │   ├── fade_in.xml
    │   ├── fade_out.xml
    │   ├── slide_in_right.xml
    │   └── slide_out_left.xml
    ├── drawable
    │   ├── ic_launcher_background.xml
    │   └── ic_movie_poster_placeholder.png
    ├── drawable-v24
    │   └── ic_launcher_foreground.xml
    ├── layout
    │   ├── activity_main.xml
    │   ├── moviecard_item.xml
    │   └── movie_item_detail.xml
    ├── layout-land
    │   └── movie_item_detail.xml
    ├── mipmap-anydpi-v26
    │   ├── ic_launcher_round.xml
    │   └── ic_launcher.xml
    ├── mipmap-hdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    ├── mipmap-mdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    ├── mipmap-xhdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    ├── mipmap-xxhdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    ├── mipmap-xxxhdpi
    │   ├── ic_launcher.png
    │   └── ic_launcher_round.png
    └── values
        ├── colors.xml
        ├── strings.xml
        └── styles.xml
```
