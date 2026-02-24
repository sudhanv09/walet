package io.github.sudhanv09.presentation.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import io.github.sudhanv09.domain.model.Account
import io.github.sudhanv09.domain.model.Category
import io.github.sudhanv09.domain.model.TransactionType
import io.github.sudhanv09.presentation.common.AppButton
import io.github.sudhanv09.presentation.common.CurrencyFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    transactionId: Long? = null,
    onNavigateBack: () -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val accounts by viewModel.accounts.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedTransaction by viewModel.selectedTransaction.collectAsState()

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var selectedAccount by remember { mutableStateOf<Account?>(null) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var accountExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var showValidation by remember { mutableStateOf(false) }

    var toAccount by remember { mutableStateOf<Account?>(null) }
    var toAccountExpanded by remember { mutableStateOf(false) }
    var hasInitializedEditState by remember(transactionId) { mutableStateOf(false) }

    LaunchedEffect(transactionId) {
        if (transactionId != null) {
            viewModel.loadTransaction(transactionId)
        } else {
            viewModel.clearSelectedTransaction()
        }
    }

    LaunchedEffect(transactionId, selectedTransaction, accounts, categories) {
        if (transactionId == null || hasInitializedEditState) return@LaunchedEffect
        val transaction = selectedTransaction ?: return@LaunchedEffect

        amount = transaction.amount.toString()
        description = transaction.description.orEmpty()
        selectedType = transaction.type
        selectedAccount = accounts.find { it.id == transaction.accountId }
        selectedCategory = categories.find { it.id == transaction.categoryId }
        toAccount = transaction.toAccountId?.let { toId -> accounts.find { it.id == toId } }
        hasInitializedEditState = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (transactionId == null) "Add Transaction" else "Edit Transaction") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TransactionType.entries.forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it.filter { c -> c.isDigit() || c == '.' } },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = showValidation && (amount.isBlank() || amount.toDoubleOrNull() == null),
                supportingText = if (showValidation && amount.toDoubleOrNull() == null) {
                    { Text("Valid amount required") }
                } else null,
                singleLine = true,
                prefix = { Text(CurrencyFormatter.currencySymbol) }
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = accountExpanded,
                onExpandedChange = { accountExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedAccount?.name ?: "Select Account",
                    onValueChange = {},
                    label = { Text("Account") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = accountExpanded) },
                    isError = showValidation && selectedAccount == null
                )
                ExposedDropdownMenu(
                    expanded = accountExpanded,
                    onDismissRequest = { accountExpanded = false }
                ) {
                    accounts.forEach { account: Account ->
                        DropdownMenuItem(
                            text = { Text("${account.name} (${CurrencyFormatter.format(account.balance)})") },
                            onClick = {
                                selectedAccount = account
                                accountExpanded = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = it }
            ) {
                val filteredCategories = categories.filter { cat: Category -> cat.type == selectedType }
                OutlinedTextField(
                    value = selectedCategory?.name ?: "Select Category",
                    onValueChange = {},
                    label = { Text("Category") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    isError = showValidation && selectedCategory == null
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    filteredCategories.forEach { category: Category ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .padding(end = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = category.icon.take(1).uppercase(),
                                            color = Color(category.color),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    Text(category.name)
                                }
                            },
                            onClick = {
                                selectedCategory = category
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            if (selectedType == TransactionType.TRANSFER) {
                ExposedDropdownMenuBox(
                    expanded = toAccountExpanded,
                    onExpandedChange = { toAccountExpanded = it }
                ) {
                    OutlinedTextField(
                        value = toAccount?.name ?: "Transfer to",
                        onValueChange = {},
                        label = { Text("To Account") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = toAccountExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = toAccountExpanded,
                        onDismissRequest = { toAccountExpanded = false }
                    ) {
                        accounts.filter { acc: Account -> acc.id != selectedAccount?.id }.forEach { account: Account ->
                            DropdownMenuItem(
                                text = { Text(account.name) },
                                onClick = {
                                    toAccount = account
                                    toAccountExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            AppButton(
                text = if (transactionId == null) "Save Transaction" else "Update Transaction",
                onClick = {
                    val amountValue = amount.toDoubleOrNull()
                    if (amountValue == null || amountValue <= 0 || selectedAccount == null || selectedCategory == null) {
                        showValidation = true
                        return@AppButton
                    }

                    if (selectedType == TransactionType.TRANSFER && toAccount == null) {
                        showValidation = true
                        return@AppButton
                    }

                    viewModel.saveTransaction(
                        id = transactionId ?: 0,
                        amount = amountValue,
                        description = description.ifBlank { null },
                        photoUri = null,
                        categoryId = selectedCategory!!.id,
                        accountId = selectedAccount!!.id,
                        type = selectedType,
                        toAccountId = if (selectedType == TransactionType.TRANSFER) toAccount!!.id else null,
                        onSuccess = onNavigateBack
                    )
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
