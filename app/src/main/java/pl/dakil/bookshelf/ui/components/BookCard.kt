package pl.dakil.bookshelf.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import pl.dakil.bookshelf.R
import pl.dakil.bookshelf.data.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    book: Book,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:")

    Card(
        modifier = modifier,
        onClick = { onClick(book.id) }
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            loading = {
                Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
                    CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
                }
            },
            error = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.volume_card_data_padding)))
                    Icon(
                        imageVector = Icons.Outlined.ImageNotSupported,
                        contentDescription = stringResource(R.string.image_loading_error)
                    )
                }
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
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