package pl.dakil.bookshelf.ui.screens.volumedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.text.HtmlCompat
import pl.dakil.bookshelf.ui.components.AppBar
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import pl.dakil.bookshelf.R
import pl.dakil.bookshelf.data.model.Book
import pl.dakil.bookshelf.ui.components.ErrorScreen
import pl.dakil.bookshelf.ui.components.InfoListItem
import pl.dakil.bookshelf.ui.components.LoadableImage
import pl.dakil.bookshelf.ui.components.LoadingScreen

private const val SINGLE_HALF_ASPECT_RATIO = 0.7f
private const val FOR_SALE_TITLE_LINES = 3
private const val NOT_FOR_SALE_TITLE_LINES = 5
private const val FOR_SALE_VALUE = "FOR_SALE"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeDetailsScreen(
    onNavigationBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VolumeDetailsViewModel = viewModel(factory = VolumeDetailsViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppBar(
                title = "Volume details",
                navigationIcon = {
                    IconButton(onClick = onNavigationBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) {
        VolumeDetailsScreenContent(
            uiState = uiState,
            onLoadingErrorRetry = {
                coroutineScope.launch {
                    viewModel.loadBook()
                }
            },
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun VolumeDetailsScreenContent(
    uiState: VolumeDetailsUiState,
    onLoadingErrorRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (uiState) {
            is VolumeDetailsUiState.Success -> {
                VolumeDetails(book = uiState.book)
            }
            is VolumeDetailsUiState.Loading -> {
                LoadingScreen(loadingText = stringResource(R.string.volume_details_loading))
            }
            is VolumeDetailsUiState.Error -> {
                ErrorScreen(
                    errorText = stringResource(R.string.volume_details_loading_error),
                    onRetry = onLoadingErrorRetry
                )
            }
        }
    }
}

@Composable
fun VolumeDetails(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.volume_details_padding))
    ) {
        BookInfoCard(book = book)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_padding)))
        VolumeDetailsList(book = book)
    }
}

@Composable
fun VolumeDetailsList(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        InfoListItem(
            headlineText = stringResource(R.string.title),
            supportingText = book.volumeInfo.title
        )
        InfoListItem(
            headlineText = stringResource(R.string.authors),
            supportingText = book.volumeInfo.authors?.joinToString(", ")
                ?: stringResource(
                    R.string.unknown_author
                )
        )
        InfoListItem(
            headlineText = stringResource(R.string.publisher),
            supportingText = if (book.volumeInfo.publisher == "") {
                stringResource(R.string.unknown_publisher)
            } else book.volumeInfo.publisher
        )
        InfoListItem(
            headlineText = stringResource(R.string.published_date),
            supportingText = book.volumeInfo.publishedDate ?: stringResource(R.string.unknown_date)
        )
        InfoListItem(
            headlineText = stringResource(R.string.description),
            supportingText = if (book.volumeInfo.description != null) {
                    HtmlCompat.fromHtml(book.volumeInfo.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        .toString().trim()
                } else stringResource(R.string.no_description)
        )
        InfoListItem(
            headlineText = stringResource(R.string.language),
            supportingText = book.volumeInfo.language ?: stringResource(R.string.unknown_language)
        )
    }
}

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
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.volume_info_card_space_between_buttons)))
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
