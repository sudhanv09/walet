package io.github.sudhanv09.domain.usecase

import io.github.sudhanv09.domain.model.Category
import io.github.sudhanv09.domain.model.TransactionType
import io.github.sudhanv09.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesByTypeUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(type: TransactionType): Flow<List<Category>> = repository.getCategoriesByType(type)
}
