package com.example.book_store.exceptions

class UnknownEnumException(value: String) : RuntimeException("$value: Response of body is empty")