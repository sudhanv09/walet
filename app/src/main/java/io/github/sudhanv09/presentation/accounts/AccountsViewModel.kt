package io.github.sudhanv09.presentation.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sudhanv09.domain.model.Account
import io.github.sudhanv09.domain.model.AccountType
import io.github.sudhanv09.domain.usecase.DeleteAccountUseCase
import io.github.sudhanv09.domain.usecase.GetAccountByIdUseCase
import io.github.sudhanv09.domain.usecase.GetAccountsUseCase
import io.github.sudhanv09.domain.usecase.SaveAccountUseCase
import io.github.sudhanv09.domain.usecase.SetDefaultAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    getAccountsUseCase: GetAccountsUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val saveAccountUseCase: SaveAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val setDefaultAccountUseCase: SetDefaultAccountUseCase
) : ViewModel() {

    val accounts = getAccountsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    fun loadAccount(accountId: Long) {
        viewModelScope.launch {
            _selectedAccount.value = getAccountByIdUseCase(accountId)
        }
    }

    fun clearSelectedAccount() {
        _selectedAccount.value = null
    }

    fun saveAccount(
        id: Long,
        name: String,
        type: AccountType,
        balance: Double,
        color: Long,
        icon: String?,
        creditLimit: Double?,
        billingDate: Int?,
        isDefault: Boolean
    ) {
        viewModelScope.launch {
            val account = Account(
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
            saveAccountUseCase(account)
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            deleteAccountUseCase(account)
        }
    }

    fun setDefaultAccount(accountId: Long) {
        viewModelScope.launch {
            setDefaultAccountUseCase(accountId)
        }
    }
}
