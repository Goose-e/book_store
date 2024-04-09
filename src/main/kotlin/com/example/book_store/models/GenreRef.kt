package com.example.book_store.models

import com.example.book_store.models.enum.GenreEnum
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Entity
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
