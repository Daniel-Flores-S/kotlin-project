package com.Market.Market.repository

import com.Market.Market.enums.BookStatus
import com.Market.Market.modal.BookModel
import com.Market.Market.modal.CustomerModel
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<BookModel, Int> {
    fun findByStatus(status: BookStatus): List<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>
}