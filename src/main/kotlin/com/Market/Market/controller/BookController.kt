package com.Market.Market.controller

import com.Market.Market.controller.request.PostBookRequest
import com.Market.Market.controller.request.PutBookRequest
import com.Market.Market.extension.toBookModel
import com.Market.Market.modal.BookModel
import com.Market.Market.service.BookService
import com.Market.Market.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("book")
class BookController(
    val bookService: BookService,
    val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: PostBookRequest) {
        val customer = customerService.findById(request.customerId!!)
        bookService.create(request.toBookModel(customer))
    }


    @GetMapping
    fun findAll(): List<BookModel> {
        return bookService.findAll()
    }

    @GetMapping("/active")
    fun findActives(): List<BookModel> {
        return bookService.findActives();
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): BookModel {
        return bookService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatedBook(@PathVariable id: Int, @RequestBody book: PutBookRequest) {
        val bookSaved = bookService.getById(id)

        bookService.updatedBook(book.toBookModel(bookSaved))
    }
}

