package com.wkylast.data.repository

import com.wkylast.domain.repository.KurlyRepository
import com.wkylast.model.entity.ProductsEntity
import com.wkylast.model.entity.SectionsEntity
import com.wkylast.network.di.Dispatcher
import com.wkylast.network.di.KurlyDispatchers
import com.wkylast.network.remote.KurlyRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class KurlyRepositoryImpl @Inject constructor(
    private val remoteDataSource: KurlyRemoteDataSource,
    @Dispatcher(KurlyDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): KurlyRepository {

    override suspend fun getSections(page: Int): SectionsEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getProducts(sectionId: Int): ProductsEntity {
        TODO("Not yet implemented")
    }
}