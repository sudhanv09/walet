package io.github.sudhanv09.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.sudhanv09.domain.model.TransactionType
import io.github.sudhanv09.presentation.accounts.AccountsViewModel
import io.github.sudhanv09.presentation.common.AppTopBar
import io.github.sudhanv09.presentation.common.CurrencyFormatter
import io.github.sudhanv09.presentation.transactions.TransactionsViewModel
import io.github.sudhanv09.ui.theme.WaletTheme
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId

@Composable
fun HomeScreen(
    onNavigateToAccounts: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToAddTransaction: () -> Unit,
    accountsViewModel: AccountsViewModel = hiltViewModel(),
    transactionsViewModel: TransactionsViewModel = hiltViewModel()
) {
    val accounts by accountsViewModel.accounts.collectAsState()
    val transactions by transactionsViewModel.transactions.collectAsState()
    val totalBalance = accounts.sumOf { it.balance }
    val currentMonth = YearMonth.now()

    val monthIncome = transactions
        .filter { it.type == TransactionType.INCOME && isInMonth(it.dateTime, currentMonth) }
        .sumOf { it.amount }

    val monthExpense = transactions
        .filter { it.type == TransactionType.EXPENSE && isInMonth(it.dateTime, currentMonth) }
        .sumOf { it.amount }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AnalyticsCard(
                    title = "Income This Month",
                    amount = monthIncome,
                    amountColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                AnalyticsCard(
                    title = "Expense This Month",
                    amount = monthExpense,
                    amountColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                )
            }

            if (accounts.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Balance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = CurrencyFormatter.format(totalBalance),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = onNavigateToAddTransaction,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Transaction")
            }

            Button(
                onClick = onNavigateToTransactions,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Transactions")
            }

            OutlinedButton(
                onClick = onNavigateToAccounts,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Manage Accounts")
            }
        }
    }
}

@Composable
private fun AnalyticsCard(
    title: String,
    amount: Double,
    amountColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = CurrencyFormatter.format(amount),
                style = MaterialTheme.typography.titleMedium,
                color = amountColor
            )
        }
    }
}

private fun isInMonth(timestamp: Long, yearMonth: YearMonth): Boolean {
    val date = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    return YearMonth.from(date) == yearMonth
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WaletTheme {
        HomeScreen(
            onNavigateToAccounts = {},
            onNavigateToTransactions = {},
            onNavigateToAddTransaction = {}
        )
    }
}
