package com.ark.nikestore.data

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)

data class PurchaseDetail(var total_price: Int, var shipping_cost: Int, var payable_price: Int)