package io.github.sudhanv09.data.mapper

import io.github.sudhanv09.data.local.entity.AccountEntity
import io.github.sudhanv09.domain.model.Account

fun Account.toEntity(): AccountEntity = AccountEntity(
    id = id,
    name = name,
    type = type,
    balance = balance,
    color = color,
    icon = icon,
    creditLimit = creditLimit,
    billingDate = billingDate,
    isDefault = isDefault
)

fun AccountEntity.toDomain(): Account = Account(
    id = id,
    name = name,
    type = type,
    balance = balance,
    color = color,
    icon = icon,
    creditLimit = creditLimit,
    billingDate = billingDate,
    isDefault = isDefault
)
