package io.github.sudhanv09.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object More

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
data class AddTransaction(val transactionId: Long? = null)

@Serializable
object Goals

@Serializable
object AddGoal

@Serializable
data class GoalDetail(val goalId: Long)

@Serializable
object SavingBuckets

@Serializable
object AddSavingBucket

@Serializable
data class SavingBucketDetail(val bucketId: Long)

@Serializable
object Settings
