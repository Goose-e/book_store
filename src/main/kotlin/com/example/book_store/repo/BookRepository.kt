package com.example.book_store.repo

import com.example.book_store.models.Book
import org.springframework.data.repository.CrudRepository

interface BookRepository:CrudRepository<Book,Int> {
}