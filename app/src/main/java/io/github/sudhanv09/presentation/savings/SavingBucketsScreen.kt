package io.github.sudhanv09.presentation.savings

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import io.github.sudhanv09.domain.model.SavingBucket
import io.github.sudhanv09.presentation.common.AppTopBar
import io.github.sudhanv09.presentation.common.CurrencyFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingBucketsScreen(
    onNavigateToAddBucket: () -> Unit,
    onNavigateToBucketDetail: (Long) -> Unit,
    viewModel: SavingBucketsViewModel = hiltViewModel()
) {
    val buckets by viewModel.savingBuckets.collectAsState()

    SavingBucketsScreenContent(
        buckets = buckets,
        onNavigateToAddBucket = onNavigateToAddBucket,
        onNavigateToBucketDetail = onNavigateToBucketDetail,
        onDeleteBucket = { viewModel.deleteSavingBucket(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SavingBucketsScreenContent(
    buckets: List<SavingBucket>,
    onNavigateToAddBucket: () -> Unit,
    onNavigateToBucketDetail: (Long) -> Unit,
    onDeleteBucket: (SavingBucket) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(title = "Saving Buckets")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddBucket) {
                Icon(Icons.Default.Add, contentDescription = "Add Bucket")
            }
        }
    ) { padding ->
        if (buckets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No saving buckets yet.")
                    Text("Tap + to create your first bucket.")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(buckets, key = { it.id }) { bucket ->
                    SavingBucketItem(
                        bucket = bucket,
                        onClick = { onNavigateToBucketDetail(bucket.id) },
                        onDelete = { onDeleteBucket(bucket) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SavingBucketItem(
    bucket: SavingBucket,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val progress = bucket.targetAmount?.let { target ->
        if (target > 0) (bucket.currentAmount / target).toFloat().coerceIn(0f, 1f) else 0f
    } ?: 0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color(bucket.color).copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = bucket.icon.take(1).uppercase(),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(bucket.color)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = bucket.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Column(
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = CurrencyFormatter.format(bucket.currentAmount),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    bucket.targetAmount?.let { target ->
                        Text(
                            text = "of ${CurrencyFormatter.format(target)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                bucket.targetAmount?.let { target ->
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(bucket.color),
                        trackColor = Color(bucket.color).copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}
