package io.github.sudhanv09.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sudhanv09.domain.model.Account
import io.github.sudhanv09.domain.model.Category
import io.github.sudhanv09.domain.model.Transaction
import io.github.sudhanv09.domain.model.TransactionType
import io.github.sudhanv09.domain.repository.AccountRepository
import io.github.sudhanv09.domain.repository.CategoryRepository
import io.github.sudhanv09.domain.usecase.DeleteTransactionUseCase
import io.github.sudhanv09.domain.usecase.GetTransactionByIdUseCase
import io.github.sudhanv09.domain.usecase.GetTransactionsUseCase
import io.github.sudhanv09.domain.usecase.SaveTransactionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    getTransactionsUseCase: GetTransactionsUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val transactions = getTransactionsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val accounts = accountRepository.getAllAccounts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories = categoryRepository.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedTransaction = MutableStateFlow<Transaction?>(null)
    val selectedTransaction: StateFlow<Transaction?> = _selectedTransaction.asStateFlow()

    fun loadTransaction(transactionId: Long) {
        viewModelScope.launch {
            _selectedTransaction.value = getTransactionByIdUseCase(transactionId)
        }
    }

    fun clearSelectedTransaction() {
        _selectedTransaction.value = null
    }

    fun saveTransaction(
        id: Long,
        amount: Double,
        description: String?,
        photoUri: String?,
        categoryId: Long,
        accountId: Long,
        type: TransactionType,
        toAccountId: Long?,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val existingTransaction = if (id != 0L) getTransactionByIdUseCase(id) else null

            existingTransaction?.let { previous ->
                val revertBalanceChange = when (previous.type) {
                    TransactionType.EXPENSE -> previous.amount
                    TransactionType.INCOME -> -previous.amount
                    TransactionType.TRANSFER -> 0.0
                }

                accountRepository.updateBalance(previous.accountId, revertBalanceChange)

                if (previous.type == TransactionType.TRANSFER && previous.toAccountId != null) {
                    accountRepository.updateBalance(previous.toAccountId, -previous.amount)
                }
            }

            val transaction = Transaction(
                id = id,
                amount = amount,
                dateTime = existingTransaction?.dateTime ?: System.currentTimeMillis(),
                description = description,
                photoUri = photoUri,
                categoryId = categoryId,
                accountId = accountId,
                toAccountId = toAccountId,
                type = type
            )
            saveTransactionUseCase(transaction)

            val balanceChange = when (type) {
                TransactionType.EXPENSE -> -amount
                TransactionType.INCOME -> amount
                TransactionType.TRANSFER -> 0.0
            }

            accountRepository.updateBalance(accountId, balanceChange)

            if (type == TransactionType.TRANSFER && toAccountId != null) {
                accountRepository.updateBalance(toAccountId, amount)
            }

            onSuccess()
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            val balanceChange = when (transaction.type) {
                TransactionType.EXPENSE -> transaction.amount
                TransactionType.INCOME -> -transaction.amount
                TransactionType.TRANSFER -> 0.0
            }

            accountRepository.updateBalance(transaction.accountId, balanceChange)

            if (transaction.type == TransactionType.TRANSFER && transaction.toAccountId != null) {
                accountRepository.updateBalance(transaction.toAccountId, -transaction.amount)
            }

            deleteTransactionUseCase(transaction)
        }
    }
}
