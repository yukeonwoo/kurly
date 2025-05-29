package com.wkylast.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(KurlyDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(KurlyDispatchers.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(KurlyDispatchers.Main)
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(KurlyDispatchers.Immediate)
    fun providesImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Dispatcher(val KurlyDispatchers: KurlyDispatchers)

enum class KurlyDispatchers {
    Default,
    IO,
    Main,
    Immediate,
}