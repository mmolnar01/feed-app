package hu.klm60o.feedapp.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.klm60o.feedapp.ui.theme.repositoryimpl.ProductRepositoryImpl
import hu.klm60o.feedapp.ui.theme.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collect


//Composable responsible for displaying the main screen
@Composable
fun MainScreen(viewModel: ProductViewModel) {
    var commandText by remember { mutableStateOf("") }

    //We collect the products from the viewModel
    val feedList by viewModel.productsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            //Display the feed content
            FeedContent(feedList)
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = commandText,
                onValueChange = { commandText = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                placeholder = { Text("Enter command")}
            )

            Spacer(modifier = Modifier.width(5.dp))

            Button(
                onClick = {
                    viewModel.processCommand(commandText)
                    commandText = ""
                }
            ) {
                Text("Enter")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(viewModel = ProductViewModel(ProductRepositoryImpl(
        apiPath = "https://dummyjson.com/products",
        limit = 1,
        skip = 0
    )))
}