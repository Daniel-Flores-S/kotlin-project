package com.mercadolivro.enums

enum class Errors(val code: String, val message: String) {
    ML0001("ML-0001", "Invalid Request"),
    ML1001("ML-1001", "Book [%s] not exists "),
    ML1002("ML-1002", "Cannot updated book with status [%s]"),
    ML2002("ML-1002", "Customer [%s] not exists ")
}