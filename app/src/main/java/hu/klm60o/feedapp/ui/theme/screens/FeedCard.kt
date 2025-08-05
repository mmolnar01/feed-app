package hu.klm60o.feedapp.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.klm60o.feedapp.ui.theme.model.Product

@Composable
fun FeedCard(
    product: Product
) {

    //If the product is a command, we give it a yellow colour
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
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = product.timestamp
                    )
                }
            }
        }
    }
    //Otherwise we give it a cyan colour
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
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 15.sp
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = product.timestamp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
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

@Preview(showBackground = true)
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