package com.Market.Market.extension

import com.Market.Market.controller.request.PostCustomerReqest
import com.Market.Market.controller.request.PutCustomerReqest
import com.Market.Market.modal.CustomerModel

fun PostCustomerReqest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email)
}

fun PutCustomerReqest.toCustomerModel(id: Int): CustomerModel {
    return CustomerModel(id = id, name = this.name, email = this.email)
}

