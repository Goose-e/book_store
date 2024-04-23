package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException

enum class GenreEnum(
    private val genreId: Int,
    private val genreCode: String,
    private val genreName: String
) {
    NO_GENRE(0, "NO_GENRE", "No genre"),
    FANTASY(1, "FANTASY", "Fantasy")
    ;

    companion object {
        fun getEnum(genreId: Int): GenreEnum =
            entries.find { it.getId() == genreId } ?: throw UnknownEnumException("privilegeId = $genreId")
    }

    fun getId() = this.genreId
    fun getCode() = this.genreCode
    fun getName() = this.genreName
}