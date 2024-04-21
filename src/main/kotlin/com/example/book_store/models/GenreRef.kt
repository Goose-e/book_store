package com.example.book_store.models

import com.example.book_store.models.enum.GenreEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "genre_ref")
data class GenreRef(
    @Id
    @Column(name = "genre_id")
    val genreId: GenreEnum,
    @Column(name = "genre_code")
    val genreCode: String,
    @Column(name = "genre_name")
    val genreName: String
)
