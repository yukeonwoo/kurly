package com.wkylast.network.remote

import com.wkylast.network.model.ProductsResponse
import com.wkylast.network.model.SectionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KurlyRemoteDataSource {

    @GET("sections")
    suspend fun getSections(
        @Query("page") page: Int,
    ): SectionsResponse

    @GET("section/products")
    suspend fun getProducts(
        @Query("sectionId") sectionId: Int,
    ): ProductsResponse
}
