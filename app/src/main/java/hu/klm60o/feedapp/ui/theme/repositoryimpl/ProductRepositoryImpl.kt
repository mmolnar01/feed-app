package hu.klm60o.feedapp.ui.theme.repositoryimpl

import hu.klm60o.feedapp.ui.theme.model.Product
import hu.klm60o.feedapp.ui.theme.repository.ProductRepository
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

    //Sets the id for the Products
    private var idCounter = 0

    //Gets the products in a list
    //If you set the limit higher, then more products are returned
    //I don't really like this solution, but I didn't want to use any 3rd party libraries
    override suspend fun getProducts(): List<Product> {
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

                    //Read the data from the JSON and create the products
                    for (i in 0 until products.length()) {
                        val product = products.getJSONObject(i)
                        val productDataClass = Product(
                            id = idCounter,
                            description = product.getString("description"),
                            thumbnail = product.getString("thumbnail"),
                            timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()),
                            isCommand = false
                        )
                        productsList.add(productDataClass)

                        //Increment id
                        idCounter++
                    }

                    //Increment skip
                    skip += limit
                    return productsList

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    //Resets the skip and the id counter
    override fun resetRepo() {
        skip = 0
    }

    //Increments the id counter by one
    override fun incrementIdCounter() {
        idCounter++
    }

    //Gets the value of the id counter
    override fun getIdCounter(): Int {
        return idCounter
    }
}