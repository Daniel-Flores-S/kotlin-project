package com.Market.Market.extension

import com.Market.Market.controller.request.PostBookRequest
import com.Market.Market.controller.request.PostCustomerReqest
import com.Market.Market.controller.request.PutBookRequest
import com.Market.Market.controller.request.PutCustomerReqest
import com.Market.Market.enums.BookStatus
import com.Market.Market.enums.CustomerStatus
import com.Market.Market.modal.BookModel
import com.Market.Market.modal.CustomerModel
import java.math.BigDecimal

fun PostCustomerReqest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email, status = CustomerStatus.ATIVO)
}

fun PutCustomerReqest.toCustomerModel(previousValue: CustomerModel): CustomerModel {
    return CustomerModel(id = previousValue.id, name = this.name, email = this.email, status = previousValue.status)
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customer
    )
}

fun PutBookRequest.toBookModel(previousValue: BookModel): BookModel {
    return BookModel(
        id = previousValue.id,
        name = this.name ?: previousValue.name,
        //Convert BigDecimal
        price = BigDecimal(this.price ?: previousValue.price.toString()),
        status = previousValue.status,
        customer = previousValue.customer
    )
}
