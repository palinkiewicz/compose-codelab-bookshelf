package pl.dakil.bookshelf.ui.screens.volumelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import pl.dakil.bookshelf.R
import pl.dakil.bookshelf.ui.components.BookCard
import pl.dakil.bookshelf.ui.components.ErrorScreen
import pl.dakil.bookshelf.ui.components.LoadingScreen
import pl.dakil.bookshelf.ui.components.SearchBarView

@Composable
fun VolumeListScreen(
    onListItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VolumeListViewModel = viewModel(factory = VolumeListViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val searchInput = viewModel.searchInput

    SearchBarView(
        searchValue = searchInput,
        onSearchInput = { viewModel.updateSearchInput(it) },
        onSearch = {
            coroutineScope.launch {
                viewModel.loadVolumes()
            }
        },
        placeholder = { Text(text = stringResource(R.string.search_volumes)) },
        modifier = modifier
    ) {
        when (val state = uiState) {
            is VolumeListUiState.Success -> {
                VolumeListGrid(uiState = state, onListItemClick = onListItemClick)
            }
            is VolumeListUiState.Loading -> {
                LoadingScreen(loadingText = stringResource(R.string.volumes_loading))
            }
            is VolumeListUiState.Error -> {
                ErrorScreen(
                    errorText = stringResource(R.string.volumes_loading_error),
                    onRetry = {
                        coroutineScope.launch {
                            viewModel.loadVolumes()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun VolumeListGrid(
    uiState: VolumeListUiState.Success,
    onListItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Adaptive(dimensionResource(R.dimen.volume_list_item_size)),
        verticalItemSpacing = dimensionResource(R.dimen.volume_list_item_spacing),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.volume_list_item_spacing)),
        contentPadding = PaddingValues(dimensionResource(R.dimen.volume_list_content_padding))
    ) {
        items(uiState.items) { book ->
            BookCard(book = book, onClick = onListItemClick)
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}
