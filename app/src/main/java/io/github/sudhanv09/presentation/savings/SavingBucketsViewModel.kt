package io.github.sudhanv09.presentation.savings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sudhanv09.domain.model.SavingBucket
import io.github.sudhanv09.domain.usecase.AddToSavingBucketUseCase
import io.github.sudhanv09.domain.usecase.DeleteSavingBucketUseCase
import io.github.sudhanv09.domain.usecase.GetSavingBucketByIdUseCase
import io.github.sudhanv09.domain.usecase.GetSavingBucketsUseCase
import io.github.sudhanv09.domain.usecase.SaveSavingBucketUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingBucketsViewModel @Inject constructor(
    getSavingBucketsUseCase: GetSavingBucketsUseCase,
    private val getSavingBucketByIdUseCase: GetSavingBucketByIdUseCase,
    private val saveSavingBucketUseCase: SaveSavingBucketUseCase,
    private val deleteSavingBucketUseCase: DeleteSavingBucketUseCase,
    private val addToSavingBucketUseCase: AddToSavingBucketUseCase
) : ViewModel() {

    val savingBuckets = getSavingBucketsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedBucket = MutableStateFlow<SavingBucket?>(null)
    val selectedBucket: StateFlow<SavingBucket?> = _selectedBucket.asStateFlow()

    fun loadBucket(bucketId: Long) {
        viewModelScope.launch {
            _selectedBucket.value = getSavingBucketByIdUseCase(bucketId)
        }
    }

    fun clearSelectedBucket() {
        _selectedBucket.value = null
    }

    fun saveSavingBucket(
        id: Long,
        name: String,
        targetAmount: Double?,
        currentAmount: Double,
        icon: String,
        color: Int,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val bucket = SavingBucket(
                id = id,
                name = name,
                targetAmount = targetAmount,
                currentAmount = currentAmount,
                icon = icon,
                color = color
            )
            saveSavingBucketUseCase(bucket)
            onSuccess()
        }
    }

    fun deleteSavingBucket(bucket: SavingBucket) {
        viewModelScope.launch {
            deleteSavingBucketUseCase(bucket)
        }
    }

    fun addToBucket(bucketId: Long, amount: Double) {
        viewModelScope.launch {
            addToSavingBucketUseCase(bucketId, amount)
        }
    }
}
