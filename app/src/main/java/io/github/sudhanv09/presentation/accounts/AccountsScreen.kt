package io.github.sudhanv09.presentation.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import io.github.sudhanv09.domain.model.Account
import io.github.sudhanv09.domain.model.AccountType
import io.github.sudhanv09.presentation.common.AppTopBar
import io.github.sudhanv09.presentation.common.CurrencyFormatter
import io.github.sudhanv09.ui.theme.WaletTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    onNavigateToAddAccount: () -> Unit,
    onNavigateToAccountDetail: (Long) -> Unit,
    viewModel: AccountsViewModel = hiltViewModel()
) {
    val accounts by viewModel.accounts.collectAsState()
    AccountsScreenContent(
        accounts = accounts,
        onNavigateToAddAccount = onNavigateToAddAccount,
        onNavigateToAccountDetail = onNavigateToAccountDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountsScreenContent(
    accounts: List<Account>,
    onNavigateToAddAccount: () -> Unit,
    onNavigateToAccountDetail: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(title = "Accounts")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddAccount) {
                Icon(Icons.Default.Add, contentDescription = "Add Account")
            }
        }
    ) { padding ->
        if (accounts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No accounts yet. Add one to get started.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                items(accounts, key = { it.id }) { account ->
                    AccountItem(
                        account = account,
                        onClick = { onNavigateToAccountDetail(account.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountItem(
    account: Account,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(account.color).copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getAccountIcon(account.type),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color(account.color)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = account.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (account.isDefault) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Default",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Text(
                    text = account.type.name.replace("_", " "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = CurrencyFormatter.format(account.balance),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

fun getAccountIcon(type: AccountType): ImageVector {
    return when (type) {
        AccountType.CASH -> Icons.Default.AccountBalanceWallet
        AccountType.BANK -> Icons.Default.AccountBalance
        AccountType.CREDIT_CARD -> Icons.Default.CreditCard
    }
}

@Preview(showBackground = true)
@Composable
fun AccountsScreenPreview() {
    WaletTheme {
        AccountsScreenContent(
            accounts = listOf(
                Account(
                    id = 1L,
                    name = "Checking Account",
                    type = AccountType.BANK,
                    balance = 2500.00,
                    color = 0xFF4CAF50,
                    isDefault = true
                ),
                Account(
                    id = 2L,
                    name = "Credit Card",
                    type = AccountType.CREDIT_CARD,
                    balance = -500.00,
                    color = 0xFFF44336,
                    isDefault = false
                ),
                Account(
                    id = 3L,
                    name = "Cash",
                    type = AccountType.CASH,
                    balance = 150.00,
                    color = 0xFF2196F3,
                    isDefault = false
                )
            ),
            onNavigateToAddAccount = {},
            onNavigateToAccountDetail = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountsScreenEmptyPreview() {
    WaletTheme {
        AccountsScreenContent(
            accounts = emptyList(),
            onNavigateToAddAccount = {},
            onNavigateToAccountDetail = {}
        )
    }
}
