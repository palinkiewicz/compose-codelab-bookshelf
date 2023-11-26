package pl.dakil.bookshelf.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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

@Composable
fun LoadableImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        loading = {
            Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
        modifier = modifier
            .fillMaxWidth()
    )
}