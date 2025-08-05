package hu.klm60o.feedapp.ui.theme.repository

import hu.klm60o.feedapp.ui.theme.model.Product


//Product repository interface
interface ProductRepository {
    //Gets a list of products
    suspend fun getProducts(): List<Product>
    fun resetRepo()
    fun incrementIdCounter()
}