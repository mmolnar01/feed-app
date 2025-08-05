package hu.klm60o.feedapp.ui.theme.repositoryimpl

import hu.klm60o.feedapp.ui.theme.model.Product
import hu.klm60o.feedapp.ui.theme.model.Response
import hu.klm60o.feedapp.ui.theme.repository.ProductRepository
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Product repository implementation
//@apiPath: Root path to the API
//@limit: How many products should be fetched
//@skip: How many products should be skipped when fetching
class ProductRepositoryImpl(
    private val apiPath: String,
    private var limit: Int,
    private var skip: Int
): ProductRepository {
    suspend override fun getProduct(): List<Product> {
        val url = "$apiPath?limit=$limit&skip=$skip"
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val stream = connection.inputStream.bufferedReader().use {
                    it.readText()
                }

                val json = JSONObject(stream)
                val products = json.getJSONArray("products")

                if (products.length() > 0) {
                    val productsList = mutableListOf<Product>()

                    for (i in 0 until products.length()) {
                        val product = products.getJSONObject(i)
                        val productDataClass = Product(
                            id = product.getLong("id"),
                            description = product.getString("description"),
                            thumbnail = product.getString("thumbnail"),
                            timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()),
                            isCommand = false
                        )
                        productsList.add(productDataClass)
                    }
                    skip += limit
                    return productsList

                    //trySend(Response.Success(productsList))

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //trySend(Response.Failure(e))
        }
        return emptyList()
    }

    override fun resetSkip() {
        skip = 0
    }
}