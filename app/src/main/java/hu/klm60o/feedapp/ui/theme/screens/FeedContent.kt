package hu.klm60o.feedapp.ui.theme.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.klm60o.feedapp.ui.theme.model.Product

@Composable
fun FeedContent(
    list: List<Product>
) {
    val listState = rememberLazyListState()

    LaunchedEffect(list) {
        if (list.isNotEmpty()) {
            listState.animateScrollToItem(list.size - 1)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
    ) {
        items(list, key = { product -> product.id }) { product ->
            FeedCard(product)
        }
    }
}