package io.github.sudhanv09.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.sudhanv09.presentation.common.AppTopBar
import io.github.sudhanv09.ui.theme.WaletTheme

@Composable
fun HomeScreen(
    onNavigateToAccounts: () -> Unit,
    onNavigateToAddTransaction: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(title = "Walet")
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Welcome to Walet",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Your personal finance tracker",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(onClick = onNavigateToAccounts) {
                Text("Manage Accounts")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WaletTheme {
        HomeScreen(
            onNavigateToAccounts = {},
            onNavigateToAddTransaction = {}
        )
    }
}
