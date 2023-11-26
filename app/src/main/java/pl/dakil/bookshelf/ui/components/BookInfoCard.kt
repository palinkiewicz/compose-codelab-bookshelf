package pl.dakil.bookshelf.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import pl.dakil.bookshelf.R
import pl.dakil.bookshelf.data.model.Book

private const val SINGLE_HALF_ASPECT_RATIO = 0.7f
private const val FOR_SALE_TITLE_LINES = 5
private const val NOT_FOR_SALE_TITLE_LINES = 7
private const val FOR_SALE_VALUE = "FOR_SALE"

@Composable
fun BookInfoCard(
    book: Book,
    modifier: Modifier = Modifier
) {
    val imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:")

    Card(modifier = modifier) {
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(SINGLE_HALF_ASPECT_RATIO)
            ) {
                LoadableImage(
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(SINGLE_HALF_ASPECT_RATIO)
                    .padding(dimensionResource(R.dimen.volume_info_card_padding)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = book.volumeInfo.title,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    maxLines = if (book.saleInfo.saleability == FOR_SALE_VALUE) FOR_SALE_TITLE_LINES else NOT_FOR_SALE_TITLE_LINES,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.volume_info_card_buttons_padding)))
                BookInfoCardButtons(book = book)
            }
        }
    }
}

@Composable
fun BookInfoCardButtons(
    book: Book,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val forSale = book.saleInfo.saleability == FOR_SALE_VALUE

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (forSale) {
            OutlinedButton(onClick = { uriHandler.openUri(book.volumeInfo.previewLink) }) {
                Text(text = stringResource(R.string.preview))
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.volume_info_card_text_padding)))
            Button(onClick = { uriHandler.openUri(book.volumeInfo.canonicalVolumeLink) }) {
                Text(text = stringResource(R.string.buy))
            }
        } else {
            OutlinedButton(onClick = { uriHandler.openUri(book.volumeInfo.previewLink) }) {
                Text(text = stringResource(R.string.more_info))
            }
        }
    }
}