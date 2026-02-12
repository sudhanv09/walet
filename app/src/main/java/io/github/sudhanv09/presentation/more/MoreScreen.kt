package io.github.sudhanv09.presentation.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.sudhanv09.presentation.common.AppTopBar

@Composable
fun MoreScreen(
    onNavigateToCategories: () -> Unit,
    onNavigateToGoals: () -> Unit,
    onNavigateToSavingBuckets: () -> Unit
) {
    Scaffold(
        topBar = { AppTopBar(title = "More") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onNavigateToCategories,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Categories")
            }

            OutlinedButton(
                onClick = onNavigateToGoals,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Goals")
            }

            OutlinedButton(
                onClick = onNavigateToSavingBuckets,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Saving Buckets")
            }
        }
    }
}
