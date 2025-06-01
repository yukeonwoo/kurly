package com.wkylast.data.repository

import com.wkylast.di.Dispatcher
import com.wkylast.di.KurlyDispatchers
import com.wkylast.domain.repository.KurlyRepository
import com.wkylast.model.entity.ProductsEntity
import com.wkylast.model.entity.SectionsEntity
import com.wkylast.network.model.toEntity
import com.wkylast.network.remote.KurlyRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class KurlyRepositoryImpl @Inject constructor(
    private val remoteDataSource: KurlyRemoteDataSource,
    @Dispatcher(KurlyDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): KurlyRepository {

    override suspend fun getSections(page: Int): SectionsEntity = withContext(ioDispatcher) {
        return@withContext remoteDataSource.getSections(page).toEntity()
    }

    override suspend fun getProducts(sectionId: Int): ProductsEntity = withContext(ioDispatcher) {
        return@withContext remoteDataSource.getProducts(sectionId).toEntity()
    }
}