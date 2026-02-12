package io.github.sudhanv09.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Accounts

@Serializable
object AddAccount

@Serializable
data class EditAccount(val accountId: Long)

@Serializable
data class AccountDetail(val accountId: Long)

@Serializable
object Categories

@Serializable
object AddCategory

@Serializable
data class EditCategory(val categoryId: Long)

@Serializable
object Transactions

@Serializable
object AddTransaction

@Serializable
data class TransactionDetail(val transactionId: Long)

@Serializable
object Goals

@Serializable
data class GoalDetail(val goalId: Long)

@Serializable
object SavingBuckets

@Serializable
data class SavingBucketDetail(val bucketId: Long)

@Serializable
object Settings
