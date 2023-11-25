package pl.dakil.bookshelf.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pl.dakil.bookshelf.R
import pl.dakil.bookshelf.data.model.Book

@Composable
fun BookCard(
    book: Book,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:")

    Card(
        modifier = modifier.clickable {
            onClick(book.id)
        }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            error = painterResource(R.drawable.ic_error_outline),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
        )
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.volume_card_data_padding))) {
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(
                modifier = Modifier.padding(dimensionResource(R.dimen.volume_card_data_between_padding))
            )
            Text(
                text = book.volumeInfo.authors?.get(0) ?: stringResource(R.string.unknown_author),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}