package com.Market.Market.service

import com.Market.Market.modal.BookModel
import com.Market.Market.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val  bookRepository: BookRepository
) {
    fun create(book: BookModel) {
        bookRepository.save(book)
    }

}
