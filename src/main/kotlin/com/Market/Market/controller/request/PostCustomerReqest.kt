package com.Market.Market.controller.request

import com.Market.Market.enums.BookStatus
import com.Market.Market.modal.BookModel
import com.Market.Market.modal.CustomerModel

data class PostCustomerReqest(
    var name: String,
    var email: String
)

