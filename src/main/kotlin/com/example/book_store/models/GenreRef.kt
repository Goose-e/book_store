package com.example.book_store.models

import com.example.book_store.models.enum.GenreEnum
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

@Table("genre_ref")
data class GenreRef(
    @Id
    @Column("genre_id")
    val genreId:GenreEnum,
    @Column("genre_code")
    val genreCode:String,
    @Column("genre_name")
    val genreName:String
)
