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

//Composable which displays the items in a LazyColumn
@Composable
fun FeedContent(
    feedList: List<Product>
) {
    val listState = rememberLazyListState()

    LaunchedEffect(feedList) {
        if (feedList.isNotEmpty()) {
            listState.animateScrollToItem(feedList.size - 1)
        }
    }

    //Display the items in a LazyColumn
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
    ) {
        items(feedList, key = { product -> product.id }) { feedItem ->
            FeedCard(feedItem)
        }
    }
}