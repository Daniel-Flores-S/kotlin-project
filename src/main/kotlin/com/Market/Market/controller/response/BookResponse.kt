package com.Market.Market.controller.response

import com.Market.Market.enums.BookStatus
import com.Market.Market.modal.CustomerModel
import java.math.BigDecimal
import javax.persistence.*

data class BookResponse(
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var customer: CustomerModel? = null,
    var status: BookStatus? = null
)
