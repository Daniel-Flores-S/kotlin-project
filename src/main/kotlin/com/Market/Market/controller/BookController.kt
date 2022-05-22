package com.Market.Market.controller

import com.Market.Market.controller.request.PostBookRequest
import com.Market.Market.controller.request.PutBookRequest
import com.Market.Market.controller.response.BookResponse
import com.Market.Market.extension.toBookModel
import com.Market.Market.extension.toResponse
import com.Market.Market.service.BookService
import com.Market.Market.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse> {
        return bookService.findAll(pageable).map { it.toResponse() }
    }

    @GetMapping("/active")
    fun findActives(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse> {
        return bookService.findActives(pageable).map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): BookResponse {
        return bookService.getById(id).toResponse();
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

