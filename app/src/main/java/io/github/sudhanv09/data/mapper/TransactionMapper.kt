package io.github.sudhanv09.data.mapper

import io.github.sudhanv09.data.local.entity.TransactionEntity
import io.github.sudhanv09.domain.model.Transaction

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    amount = amount,
    dateTime = dateTime,
    description = description,
    photoUri = photoUri,
    categoryId = categoryId,
    accountId = accountId,
    toAccountId = toAccountId,
    type = type
)

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    amount = amount,
    dateTime = dateTime,
    description = description,
    photoUri = photoUri,
    categoryId = categoryId,
    accountId = accountId,
    toAccountId = toAccountId,
    type = type
)
