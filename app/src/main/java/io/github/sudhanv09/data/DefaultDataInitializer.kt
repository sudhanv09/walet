package io.github.sudhanv09.data

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import io.github.sudhanv09.domain.model.Account
import io.github.sudhanv09.domain.model.AccountType
import io.github.sudhanv09.domain.model.Category
import io.github.sudhanv09.domain.model.TransactionType
import io.github.sudhanv09.domain.repository.AccountRepository
import io.github.sudhanv09.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.first

class DefaultDataInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val appContext = context.applicationContext
        val entryPoint = EntryPointAccessors.fromApplication(
            appContext,
            InitializerEntryPoint::class.java
        )

        val accountRepository = entryPoint.accountRepository()
        val categoryRepository = entryPoint.categoryRepository()

        kotlinx.coroutines.runBlocking {
            val accounts = accountRepository.getAllAccounts().first()
            if (accounts.isEmpty()) {
                val cashAccount = Account(
                    name = "Cash",
                    type = AccountType.CASH,
                    balance = 0.0,
                    color = 0xFF4CAF50,
                    isDefault = true
                )
                accountRepository.insertAccount(cashAccount)

                val expenseCategories = listOf(
                    Category(name = "Shopping", icon = "ShoppingCart", color = 0xFFE91E63, type = TransactionType.EXPENSE, isDefault = true),
                    Category(name = "Bills", icon = "Receipt", color = 0xFF9C27B0, type = TransactionType.EXPENSE, isDefault = true),
                    Category(name = "Food", icon = "Restaurant", color = 0xFFFF9800, type = TransactionType.EXPENSE, isDefault = true),
                    Category(name = "Transport", icon = "DirectionsCar", color = 0xFF2196F3, type = TransactionType.EXPENSE, isDefault = true),
                    Category(name = "Entertainment", icon = "Movie", color = 0xFFF44336, type = TransactionType.EXPENSE, isDefault = true),
                    Category(name = "Health", icon = "LocalHospital", color = 0xFF4CAF50, type = TransactionType.EXPENSE, isDefault = true),
                    Category(name = "Education", icon = "School", color = 0xFF00BCD4, type = TransactionType.EXPENSE, isDefault = true),
                    Category(name = "Others", icon = "MoreHoriz", color = 0xFF607D8B, type = TransactionType.EXPENSE, isDefault = true)
                )

                val incomeCategories = listOf(
                    Category(name = "Salary", icon = "AccountBalance", color = 0xFF4CAF50, type = TransactionType.INCOME, isDefault = true),
                    Category(name = "Freelance", icon = "Work", color = 0xFF2196F3, type = TransactionType.INCOME, isDefault = true),
                    Category(name = "Investment", icon = "TrendingUp", color = 0xFF9C27B0, type = TransactionType.INCOME, isDefault = true),
                    Category(name = "Gift", icon = "CardGiftcard", color = 0xFFFF9800, type = TransactionType.INCOME, isDefault = true),
                    Category(name = "Refund", icon = "Restore", color = 0xFF00BCD4, type = TransactionType.INCOME, isDefault = true),
                    Category(name = "Others", icon = "MoreHoriz", color = 0xFF607D8B, type = TransactionType.INCOME, isDefault = true)
                )

                categoryRepository.insertCategories(expenseCategories + incomeCategories)
            }
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface InitializerEntryPoint {
        fun accountRepository(): AccountRepository
        fun categoryRepository(): CategoryRepository
    }
}
