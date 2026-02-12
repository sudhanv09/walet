package io.github.sudhanv09.presentation.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sudhanv09.domain.model.Goal
import io.github.sudhanv09.domain.usecase.AddToGoalUseCase
import io.github.sudhanv09.domain.usecase.DeleteGoalUseCase
import io.github.sudhanv09.domain.usecase.GetGoalByIdUseCase
import io.github.sudhanv09.domain.usecase.GetGoalsUseCase
import io.github.sudhanv09.domain.usecase.SaveGoalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    getGoalsUseCase: GetGoalsUseCase,
    private val getGoalByIdUseCase: GetGoalByIdUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val addToGoalUseCase: AddToGoalUseCase
) : ViewModel() {

    val goals = getGoalsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedGoal = MutableStateFlow<Goal?>(null)
    val selectedGoal: StateFlow<Goal?> = _selectedGoal.asStateFlow()

    fun loadGoal(goalId: Long) {
        viewModelScope.launch {
            _selectedGoal.value = getGoalByIdUseCase(goalId)
        }
    }

    fun clearSelectedGoal() {
        _selectedGoal.value = null
    }

    fun saveGoal(
        id: Long,
        name: String,
        targetAmount: Double,
        currentAmount: Double,
        deadline: Long?,
        icon: String,
        color: Int,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val goal = Goal(
                id = id,
                name = name,
                targetAmount = targetAmount,
                currentAmount = currentAmount,
                deadline = deadline,
                icon = icon,
                color = color
            )
            saveGoalUseCase(goal)
            onSuccess()
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch {
            deleteGoalUseCase(goal)
        }
    }

    fun addToGoal(goalId: Long, amount: Double) {
        viewModelScope.launch {
            addToGoalUseCase(goalId, amount)
        }
    }
}
