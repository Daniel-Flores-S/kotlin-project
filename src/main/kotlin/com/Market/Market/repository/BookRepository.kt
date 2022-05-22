package com.Market.Market.repository

import com.Market.Market.enums.BookStatus
import com.Market.Market.modal.BookModel
import com.Market.Market.modal.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<BookModel, Int> {
    fun findByStatus(status: BookStatus, pageable: Pageable): Page<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>
    // fun findAll(pageable: Pageable): Page<BookModel>
}