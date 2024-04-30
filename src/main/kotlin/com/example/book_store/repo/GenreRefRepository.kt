package com.example.book_store.repo

import com.example.book_store.models.GenreRef
import org.springframework.data.repository.CrudRepository

interface GenreRefRepository:CrudRepository<GenreRef,Int>