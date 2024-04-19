package com.example.book_store.dao

import com.example.book_store.repo.BookRepository
import org.springframework.stereotype.Component


@Component
class BookDao(private val bookRepo: BookRepository) {
}