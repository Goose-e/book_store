package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException

enum class GenreEnum(
    private val genreId: Long,
    private val genreCode: String,
    private val genreName: String
) {
    NO_GENRE(1, "NO_GENRE", "No genre")
    ;

    companion object {
        fun getEnum(genreId: Long): GenreEnum =
            entries.find { it.getId() == genreId } ?: throw UnknownEnumException("privilegeId = $genreId")
    }

    fun getId() = this.genreId
    fun getCode() = this.genreCode
    fun getName() = this.genreName
}