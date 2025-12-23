package com.svillarreal.fakestorechallenge.ui.navigation


object Routes {
    const val PRODUCTS = "products"
    const val ARG_PRODUCT_ID = "productId"
    const val PRODUCT_DETAIL = "product/{$ARG_PRODUCT_ID}"

    fun productDetail(productId: Int) = "product/$productId"
}