package io.github.sudhanv09.presentation.accounts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import io.github.sudhanv09.domain.model.AccountType
import io.github.sudhanv09.presentation.common.AppButton
import io.github.sudhanv09.presentation.common.AppTopBar

private val defaultColors = listOf(
    Color(0xFF4CAF50),
    Color(0xFF2196F3),
    Color(0xFFFF9800),
    Color(0xFF9C27B0),
    Color(0xFFF44336),
    Color(0xFF00BCD4)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAccountScreen(
    accountId: Long?,
    onNavigateBack: () -> Unit,
    viewModel: AccountsViewModel = hiltViewModel()
) {
    val selectedAccount by viewModel.selectedAccount.collectAsState()

    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(AccountType.CASH) }
    var balance by remember { mutableStateOf("") }
    var selectedColor by remember { mutableLongStateOf(defaultColors.first().toArgb().toLong()) }
    var creditLimit by remember { mutableStateOf("") }
    var billingDate by remember { mutableStateOf("") }
    var isDefault by remember { mutableStateOf(false) }

    var showValidation by remember { mutableStateOf(false) }
    var hasInitializedEditState by remember(accountId) { mutableStateOf(false) }

    val isCreditCard = selectedType == AccountType.CREDIT_CARD

    LaunchedEffect(accountId) {
        if (accountId != null) {
            viewModel.loadAccount(accountId)
        }
    }

    LaunchedEffect(accountId, selectedAccount) {
        if (accountId == null || hasInitializedEditState) return@LaunchedEffect
        val account = selectedAccount ?: return@LaunchedEffect

        name = account.name
        selectedType = account.type
        balance = if (account.balance == 0.0) "" else account.balance.toString()
        selectedColor = account.color
        creditLimit = account.creditLimit?.toString().orEmpty()
        billingDate = account.billingDate?.toString().orEmpty()
        isDefault = account.isDefault
        hasInitializedEditState = true
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (accountId == null) "Add Account" else "Edit Account",
                onNavigateBack = onNavigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Account Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = showValidation && name.isBlank(),
                supportingText = if (showValidation && name.isBlank()) {
                    { Text("Name is required") }
                } else null,
                singleLine = true
            )

            Text(
                text = "Account Type",
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AccountType.entries.forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type.name.replace("_", " ")) },
                        leadingIcon = {
                            when (type) {
                                AccountType.CASH -> Icons.Default.AccountBalanceWallet
                                AccountType.BANK -> Icons.Default.AccountBalance
                                AccountType.CREDIT_CARD -> Icons.Default.CreditCard
                            }.let { icon ->
                                Icon(imageVector = icon, contentDescription = null)
                            }
                        }
                    )
                }
            }

            OutlinedTextField(
                value = balance,
                onValueChange = { balance = it.filter { c -> c.isDigit() || c == '.' } },
                label = { Text("Initial Balance") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            if (isCreditCard) {
                OutlinedTextField(
                    value = creditLimit,
                    onValueChange = { creditLimit = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Credit Limit") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                OutlinedTextField(
                    value = billingDate,
                    onValueChange = { billingDate = it.filter { c -> c.isDigit() }.take(2) },
                    label = { Text("Billing Date (Day of Month)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            Text(
                text = "Color",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                defaultColors.forEach { color ->
                    val isSelected = color.toArgb().toLong() == selectedColor
                    ColorOption(
                        color = color,
                        isSelected = isSelected,
                        onClick = { selectedColor = color.toArgb().toLong() }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isDefault,
                    onCheckedChange = { isDefault = it }
                )
                Text("Set as default account")
            }

            AppButton(
                text = if (accountId == null) "Add Account" else "Save Changes",
                onClick = {
                    if (name.isBlank()) {
                        showValidation = true
                        return@AppButton
                    }

                    viewModel.saveAccount(
                        id = accountId ?: 0,
                        name = name,
                        type = selectedType,
                        balance = balance.toDoubleOrNull() ?: 0.0,
                        color = selectedColor,
                        icon = null,
                        creditLimit = creditLimit.toDoubleOrNull(),
                        billingDate = billingDate.toIntOrNull(),
                        isDefault = isDefault
                    )
                    onNavigateBack()
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
    }
}
