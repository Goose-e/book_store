package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException

enum class GenreEnum(
    private val genreId: Int,
    private val genreCode: String,
    private val genreName: String
) {
    NO_GENRE(0, "NO_GENRE", "NO_GENRE"),
    FANTASY(1, "FANTASY", "FANTASY"),
    SCIENCE_FICTION(2, "SCIENCE_FICTION", "SCIENCE_FICTION"),
    ROMANCE(3, "ROMANCE", "ROMANCE"),
    MYSTERY(4, "MYSTERY", "MYSTERY"),
    HORROR(5, "HORROR", "HORROR"),
    THRILLER(6, "THRILLER", "THRILLER"),
    HISTORICAL(7, "HISTORICAL", "HISTORICAL"),
    CHILDREN(8, "CHILDREN", "CHILDREN"),
    YOUNG_ADULT(9, "YOUNG_ADULT", "YOUNG_ADULT");


    companion object {
        fun getEnum(genreId: Int): GenreEnum =
            entries.find { it.getId() == genreId } ?: throw UnknownEnumException("privilegeId = $genreId")
    }

    fun getId() = this.genreId
    fun getCode() = this.genreCode
    fun getName() = this.genreName
}