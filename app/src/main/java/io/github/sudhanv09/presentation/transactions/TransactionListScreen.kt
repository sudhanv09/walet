package io.github.sudhanv09.presentation.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import io.github.sudhanv09.domain.model.Category
import io.github.sudhanv09.domain.model.Transaction
import io.github.sudhanv09.domain.model.TransactionType
import io.github.sudhanv09.presentation.common.AppTopBar
import io.github.sudhanv09.presentation.common.CurrencyFormatter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToTransactionDetail: (Long) -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val transactions by viewModel.transactions.collectAsState()
    val categories by viewModel.categories.collectAsState()

    TransactionListScreenContent(
        transactions = transactions,
        categories = categories,
        onNavigateToAddTransaction = onNavigateToAddTransaction,
        onNavigateToTransactionDetail = onNavigateToTransactionDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionListScreenContent(
    transactions: List<Transaction>,
    categories: List<Category>,
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToTransactionDetail: (Long) -> Unit
) {
    val monthOptions = remember { last12Months() }
    var selectedMonth by remember { mutableStateOf(YearMonth.now()) }
    val monthLabelFormatter = remember { DateTimeFormatter.ofPattern("MMM yyyy", Locale.getDefault()) }
    val selectedMonthTransactions = transactions.filter { transactionYearMonth(it.dateTime) == selectedMonth }
    val selectedTabIndex = monthOptions.indexOf(selectedMonth).let { if (it >= 0) it else monthOptions.lastIndex }

    Scaffold(
        topBar = {
            AppTopBar(title = "Transactions")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddTransaction) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { padding ->
        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No transactions yet.")
                    Text("Tap + to add your first transaction.")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
                    monthOptions.forEach { month ->
                        Tab(
                            selected = month == selectedMonth,
                            onClick = { selectedMonth = month },
                            text = { Text(month.format(monthLabelFormatter)) }
                        )
                    }
                }

                if (selectedMonthTransactions.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No transactions in ${selectedMonth.format(monthLabelFormatter)}.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(selectedMonthTransactions, key = { it.id }) { transaction ->
                            val category = categories.find { it.id == transaction.categoryId }
                            TransactionItem(
                                transaction = transaction,
                                category = category,
                                onClick = { onNavigateToTransactionDetail(transaction.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun last12Months(): List<YearMonth> {
    val currentMonth = YearMonth.now()
    return (11 downTo 0).map { offset -> currentMonth.minusMonths(offset.toLong()) }
}

private fun transactionYearMonth(timestamp: Long): YearMonth {
    return YearMonth.from(
        Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    )
}

@Composable
private fun TransactionItem(
    transaction: Transaction,
    category: Category?,
    onClick: () -> Unit
) {
    val isExpense = transaction.type == TransactionType.EXPENSE
    val isIncome = transaction.type == TransactionType.INCOME
    val isTransfer = transaction.type == TransactionType.TRANSFER

    val amountColor = when {
        isExpense -> MaterialTheme.colorScheme.error
        isIncome -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }

    val icon = when {
        isExpense -> Icons.Default.ArrowDownward
        isIncome -> Icons.Default.ArrowUpward
        else -> Icons.Default.SwapHoriz
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = amountColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = category?.name ?: "Unknown Category",
                    style = MaterialTheme.typography.titleMedium
                )
                if (!transaction.description.isNullOrBlank()) {
                    Text(
                        text = transaction.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = formatDate(transaction.dateTime),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                val displayAmount = when {
                    isExpense -> "-${CurrencyFormatter.format(transaction.amount)}"
                    isIncome -> "+${CurrencyFormatter.format(transaction.amount)}"
                    else -> CurrencyFormatter.format(transaction.amount)
                }
                Text(
                    text = displayAmount,
                    style = MaterialTheme.typography.titleMedium,
                    color = amountColor
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
