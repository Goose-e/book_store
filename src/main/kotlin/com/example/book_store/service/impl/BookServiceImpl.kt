package com.example.book_store.service.impl

import com.example.book_store.dao.BookDao
import com.example.book_store.dto.BookDTO
import com.example.book_store.dto.CreateOrUpdateBookRequestDto
import com.example.book_store.dto.CreatedBookDto
import com.example.book_store.service.BookService
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    val bookDao: BookDao
) : BookService {

    override fun addBook(bookRequestDto: CreateOrUpdateBookRequestDto): CreatedBookDto {
        // add logic
        /*
        * 1. валидирование полей
        * 2. создание объекта книга (в маппере)
        * 3. создание сущности core_entity
        * 4. сохранение в базу двух таблиц в транзакции
        * 5. создание объекта CreatedBookDto - вернуть в конце функции
        *
        * Важно: отсюда обращаешься к уровню ДАО, если нужно что-то сохранить или взять из бд
        * Из дао обращаешься в базу данных
        *
        * Подсказка:
        * Т.к. это и создание, и апдейт, то код будет передаваться
        * То есть, ты ищешь в базе по коду, если он не налл
        * И обновляешь эту сущность
        * Если код налл, то создаешь новую
        *
        * Пока на уникальность в остальном не проверяем, потом надо будет
        * */
        TODO("Not yet implemented")
    }

    override fun deleteBook(bookCode: BookDTO): BookDTO {
        TODO("Not yet implemented")
    }

    override fun getBook(bookName: BookDTO): BookDTO {
        /*
        * По аналогии с созданием (пишешь контроллер, дтошки)
        * Поиск по названию, код и получение описания книги
        * */
        TODO("Not yet implemented")
    }

    override fun getAllBooks(): List<BookDTO> {
        TODO("Not yet implemented")
    }

    override fun updateBook(bookDTO: BookDTO): BookDTO {
        TODO("Not yet implemented")
    }

}