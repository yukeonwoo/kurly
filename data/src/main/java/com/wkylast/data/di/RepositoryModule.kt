package com.wkylast.data.di

import com.wkylast.data.repository.KurlyRepositoryImpl
import com.wkylast.domain.repository.KurlyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(
        kurlyRepositoryImpl: KurlyRepositoryImpl
    ): KurlyRepository
}