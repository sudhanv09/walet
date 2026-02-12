package io.github.sudhanv09.data.repository

import io.github.sudhanv09.data.local.dao.CategoryDao
import io.github.sudhanv09.data.mapper.toDomain
import io.github.sudhanv09.data.mapper.toEntity
import io.github.sudhanv09.domain.model.Category
import io.github.sudhanv09.domain.model.TransactionType
import io.github.sudhanv09.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun getAllCategories(): Flow<List<Category>> =
        categoryDao.getAllCategories().map { it.map { entity -> entity.toDomain() } }

    override fun getCategoriesByType(type: TransactionType): Flow<List<Category>> =
        categoryDao.getCategoriesByType(type).map { it.map { entity -> entity.toDomain() } }

    override suspend fun getCategoryById(id: Long): Category? =
        categoryDao.getCategoryById(id)?.toDomain()

    override suspend fun insertCategory(category: Category): Long =
        categoryDao.insertCategory(category.toEntity())

    override suspend fun insertCategories(categories: List<Category>) =
        categoryDao.insertCategories(categories.map { it.toEntity() })

    override suspend fun updateCategory(category: Category) =
        categoryDao.updateCategory(category.toEntity())

    override suspend fun deleteCategory(category: Category) =
        categoryDao.deleteCategory(category.toEntity())
}
