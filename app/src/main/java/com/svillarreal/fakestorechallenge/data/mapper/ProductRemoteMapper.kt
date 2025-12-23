package com.svillarreal.fakestorechallenge.data.mapper

import com.svillarreal.fakestorechallenge.data.local.entity.ProductEntity
import com.svillarreal.fakestorechallenge.data.remote.model.ProductResponse
import com.svillarreal.fakestorechallenge.data.remote.model.RatingResponse
import com.svillarreal.fakestorechallenge.domain.model.Product
import com.svillarreal.fakestorechallenge.domain.model.Rating

fun ProductResponse.toEntity(updatedAt: Long): ProductEntity =
    ProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        updatedAt = updatedAt
    )

fun ProductEntity.toDomain(): Product =
    Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image
    )