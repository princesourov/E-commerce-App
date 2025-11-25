package com.epikason.ozzoapp.data.models

data class Product(
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var amount: Int = 0,
    var imageLink: String = "",
    var sellerID: String = "",
    var productID: String = ""
)