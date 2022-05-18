package com.Market.Market.service

import com.Market.Market.controller.request.PutBookRequest
import com.Market.Market.enums.BookStatus
import com.Market.Market.modal.BookModel
import com.Market.Market.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(): List<BookModel> {
        return bookRepository.findAll().toList()
    }

    fun findActives(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    fun getById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow()
    }

    fun delete(id: Int) {
        val book = getById(id)
        book.status = BookStatus.CANCELADO
        updatedBook(book)
    }

    fun updatedBook(book: BookModel) {
            bookRepository.save(book)
    }


}
