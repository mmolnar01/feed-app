package hu.klm60o.feedapp.ui.theme.repository

import hu.klm60o.feedapp.ui.theme.model.Product
import hu.klm60o.feedapp.ui.theme.model.Response
import kotlinx.coroutines.flow.Flow

typealias ProductsResponse = Response<List<Product>>

//Product repository interface
interface ProductRepository {
    //Gets a single product
    suspend fun getProduct(): List<Product>
    fun resetSkip()
}