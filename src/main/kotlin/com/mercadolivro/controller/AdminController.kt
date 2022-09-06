package com.mercadolivro.controller

import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.extension.toResponse
import com.mercadolivro.service.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin")
class AdminController(
    val customerService : CustomerService
) {

    @GetMapping("/report")
    fun report(): String {
        return "This is a Report. Only Admin can see it!"
    }



}