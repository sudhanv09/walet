package io.github.sudhanv09.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sudhanv09.domain.model.Category
import io.github.sudhanv09.domain.model.TransactionType
import io.github.sudhanv09.domain.usecase.DeleteCategoryUseCase
import io.github.sudhanv09.domain.usecase.GetCategoriesByTypeUseCase
import io.github.sudhanv09.domain.usecase.GetCategoriesUseCase
import io.github.sudhanv09.domain.usecase.SaveCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase,
    private val saveCategoryUseCase: SaveCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel() {

    val categories = getCategoriesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedType = MutableStateFlow<TransactionType?>(null)
    val selectedType: StateFlow<TransactionType?> = _selectedType.asStateFlow()

    private val _filteredCategories = MutableStateFlow<List<Category>>(emptyList())
    val filteredCategories: StateFlow<List<Category>> = _filteredCategories.asStateFlow()

    fun selectType(type: TransactionType?) {
        _selectedType.value = type
        if (type != null) {
            viewModelScope.launch {
                getCategoriesByTypeUseCase(type).collect { categories ->
                    _filteredCategories.value = categories
                }
            }
        } else {
            _filteredCategories.value = categories.value
        }
    }

    fun saveCategory(
        id: Long,
        name: String,
        icon: String,
        color: Long,
        type: TransactionType,
        isDefault: Boolean
    ) {
        viewModelScope.launch {
            val category = Category(
                id = id,
                name = name,
                icon = icon,
                color = color,
                type = type,
                isDefault = isDefault
            )
            saveCategoryUseCase(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            deleteCategoryUseCase(category)
        }
    }
}
