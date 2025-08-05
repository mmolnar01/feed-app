package hu.klm60o.feedapp.ui.theme.model

import java.sql.Timestamp


//Product Data class to fetch data from a JSON file
data class Product(
    val id: Int,
    val description: String,
    val thumbnail: String?,
    val timestamp: String,
    val isCommand: Boolean
)
