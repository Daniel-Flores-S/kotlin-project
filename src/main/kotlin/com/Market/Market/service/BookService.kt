package com.Market.Market.service

import com.Market.Market.controller.request.PutBookRequest
import com.Market.Market.enums.BookStatus
import com.Market.Market.modal.BookModel
import com.Market.Market.modal.CustomerModel
import com.Market.Market.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(pageable: Pageable): Page<BookModel> {
        //return bookRepository.findAll().toList() => antigo com lista de livros
        return bookRepository.findAll(pageable)
    }

    fun findActives(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
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

    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomer(customer)

        for (book in books) {
            book.status = BookStatus.DELETADO
        }

        bookRepository.saveAll(books)
    }


}
