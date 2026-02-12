package io.github.sudhanv09.data.mapper

import io.github.sudhanv09.data.local.entity.CategoryEntity
import io.github.sudhanv09.domain.model.Category

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    icon = icon,
    color = color,
    type = type,
    isDefault = isDefault
)

fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    icon = icon,
    color = color,
    type = type,
    isDefault = isDefault
)
