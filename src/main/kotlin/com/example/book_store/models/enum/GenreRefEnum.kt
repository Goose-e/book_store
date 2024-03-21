package com.example.book_store.models.enum

import com.example.book_store.exceptions.UnknownEnumException

enum class GenreRefEnum(
    private val genreId: Long,
    private val genreCode: String,
    private val genreName: String
) {
    ;

    companion object {
        fun getEnum(genreId: Long): GenreRefEnum =
            entries.find { it.getId() == genreId } ?: throw UnknownEnumException("privilegeId = $genreId")
    }

    fun getId() = this.genreId
    fun getCode() = this.genreCode
    fun getName() = this.genreName
}