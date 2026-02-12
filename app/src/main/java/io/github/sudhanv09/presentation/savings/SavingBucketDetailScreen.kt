package io.github.sudhanv09.presentation.savings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.sudhanv09.presentation.common.AppButton
import io.github.sudhanv09.presentation.common.CurrencyFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingBucketDetailScreen(
    bucketId: Long,
    onNavigateBack: () -> Unit,
    viewModel: SavingBucketsViewModel = hiltViewModel()
) {
    val bucket by viewModel.selectedBucket.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showAddAmountSheet by remember { mutableStateOf(false) }
    var amountToAdd by remember { mutableStateOf("") }
    var showValidation by remember { mutableStateOf(false) }

    LaunchedEffect(bucketId) {
        viewModel.loadBucket(bucketId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bucket Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    bucket?.let { b ->
                        IconButton(onClick = {
                            viewModel.deleteSavingBucket(b)
                            onNavigateBack()
                        }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddAmountSheet = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Amount")
            }
        }
    ) { padding ->
        bucket?.let { b ->
            val progress = b.targetAmount?.let { target ->
                if (target > 0) (b.currentAmount / target).toFloat().coerceIn(0f, 1f) else 0f
            } ?: 0f

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(b.color).copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = b.icon.take(1).uppercase(),
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color(b.color)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = b.name,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = CurrencyFormatter.format(b.currentAmount),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(b.color)
                        )

                        b.targetAmount?.let { target ->
                            Text(
                                text = "of ${CurrencyFormatter.format(target)}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                color = Color(b.color),
                                trackColor = Color(b.color).copy(alpha = 0.2f)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${(progress * 100).toInt()}% complete",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            val remaining = target - b.currentAmount
                            if (remaining > 0) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "${CurrencyFormatter.format(remaining)} remaining",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } ?: run {
                            Text(
                                text = "No target set",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        if (showAddAmountSheet) {
            ModalBottomSheet(
                onDismissRequest = { showAddAmountSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Add to Bucket",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    OutlinedTextField(
                        value = amountToAdd,
                        onValueChange = { amountToAdd = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Amount") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        prefix = { Text(CurrencyFormatter.currencySymbol) }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(
                            onClick = { showAddAmountSheet = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                        AppButton(
                            onClick = {
                                val amount = amountToAdd.toDoubleOrNull()
                                if (amount != null && amount > 0) {
                                    viewModel.addToBucket(bucketId, amount)
                                    showAddAmountSheet = false
                                    amountToAdd = ""
                                } else {
                                    showValidation = true
                                }
                            },
                            modifier = Modifier.weight(1f),
                            text = "Add"
                        )
                    }
                }
            }
        }
    }
}
