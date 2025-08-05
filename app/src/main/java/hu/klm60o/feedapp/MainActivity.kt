package hu.klm60o.feedapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import hu.klm60o.feedapp.ui.theme.FeedAppTheme
import hu.klm60o.feedapp.ui.theme.repositoryimpl.ProductRepositoryImpl
import hu.klm60o.feedapp.ui.theme.screens.MainScreen
import hu.klm60o.feedapp.ui.theme.viewmodel.ProductViewModel
import hu.klm60o.feedapp.ui.theme.viewmodel.ProductViewModelFactory

//The main activity of the application
//Responsible for creating the viewModel
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Dependency injection
        val productRepository = ProductRepositoryImpl(
            apiPath = "https://dummyjson.com/products",
            limit = 1,
            skip = 0
        )

        viewModel = ViewModelProvider(this, ProductViewModelFactory(productRepository)).get(ProductViewModel::class.java)

        enableEdgeToEdge()
        setContent {
            FeedAppTheme {
                Scaffold { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen(viewModel)
                    }
                }

            }
        }
    }
}