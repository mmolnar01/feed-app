package hu.klm60o.feedapp.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.klm60o.feedapp.ui.theme.model.Product

@Composable
fun FeedCard(
    product: Product
) {
    if (product.isCommand) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = MaterialTheme.shapes.small,
            elevation = CardDefaults.cardElevation(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Yellow
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(
                        text = product.description,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = product.timestamp
                    )
                }
            }
        }
    }
    else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = MaterialTheme.shapes.small,
            elevation = CardDefaults.cardElevation(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Cyan
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(
                        text = product.description,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row {
                    Text(
                        text = product.timestamp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedItemPreview() {
    FeedCard(Product(
        id = 1,
        description = "Tesztelés",
        timestamp = "1111",
        isCommand = false,
        thumbnail = null
    ))
}

@Preview
@Composable
fun CommandPreview() {
    FeedCard(Product(
        id = 1,
        description = "Tesztelés",
        timestamp = "1111",
        isCommand = true,
        thumbnail = null
    ))
}