package com.Market.Market.controller

import com.Market.Market.controller.request.PostBookRequest
import com.Market.Market.extension.toBookModel
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
}

