package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Category
import io.github.sudhanv09.domain.repository.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category): Long = repository.insertCategory(category)
}
