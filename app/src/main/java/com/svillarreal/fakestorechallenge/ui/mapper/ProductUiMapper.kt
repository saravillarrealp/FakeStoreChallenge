package com.svillarreal.fakestorechallenge.ui.mapper

import com.svillarreal.fakestorechallenge.domain.model.Product
import com.svillarreal.fakestorechallenge.ui.model.ProductDetailViewData
import com.svillarreal.fakestorechallenge.ui.model.ProductViewData

fun Product.toViewData(): ProductViewData =
    ProductViewData(
        id  = id,
        title = title,
        priceFormatted = "$${price}",
        category = category,
        imageUrl = image
    )

fun Product.toDetailViewData(isFavorite: Boolean): ProductDetailViewData =
    ProductDetailViewData(
        id = id,
        title = title,
        description = description,
        category = category,
        priceFormatted = "$$price",
        imageUrl = image,
        isFavorite = isFavorite
    )