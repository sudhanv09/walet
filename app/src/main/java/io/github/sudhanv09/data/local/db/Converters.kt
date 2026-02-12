package io.github.sudhanv09.data.local.db

import androidx.room.TypeConverter
import io.github.sudhanv09.domain.model.AccountType
import io.github.sudhanv09.domain.model.TransactionType

class Converters {
    @TypeConverter
    fun fromAccountType(value: AccountType): String {
        return value.name
    }

    @TypeConverter
    fun toAccountType(value: String): AccountType {
        return AccountType.valueOf(value)
    }

    @TypeConverter
    fun fromTransactionType(value: TransactionType): String {
        return value.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}
