package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException

enum class GenreEnum(
    private val genreId: Int,
    private val genreCode: String,
    private val genreName: String
) {
    NO_GENRE(0, "NO_GENRE", "No genre"),
    FANTASY(1, "FANTASY", "Fantasy"),
    SCIENCE_FICTION(2, "SCIENCE_FICTION", "Science Fiction"),
    ROMANCE(3, "ROMANCE", "Romance"),
    MYSTERY(4, "MYSTERY", "Mystery"),
    HORROR(5, "HORROR", "Horror"),
    THRILLER(6, "THRILLER", "Thriller"),
    HISTORICAL(7, "HISTORICAL", "Historical"),
    CHILDREN(8, "CHILDREN", "Children"),
    YOUNG_ADULT(9, "YOUNG_ADULT", "Young Adult");


    companion object {
        fun getEnum(genreId: Int): GenreEnum =
            entries.find { it.getId() == genreId } ?: throw UnknownEnumException("privilegeId = $genreId")
    }

    fun getId() = this.genreId
    fun getCode() = this.genreCode
    fun getName() = this.genreName
}