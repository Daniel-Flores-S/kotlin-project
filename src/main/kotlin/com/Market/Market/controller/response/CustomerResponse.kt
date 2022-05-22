package com.Market.Market.controller.response

import com.Market.Market.enums.CustomerStatus
import javax.persistence.*

data class CustomerResponse(
    var id: Int? = null,


    var name: String,


    var email: String,


    var status: CustomerStatus
) {


}
