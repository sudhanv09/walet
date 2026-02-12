package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Category
import io.github.sudhanv09.domain.repository.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) = repository.deleteCategory(category)
}
