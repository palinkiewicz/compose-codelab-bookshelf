package pl.dakil.bookshelf.ui.screens.volumelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import pl.dakil.bookshelf.R
import pl.dakil.bookshelf.ui.components.BookCard
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
                VolumeListLoading()
            }
            is VolumeListUiState.Error -> {
                VolumeListError(onRetry = {
                    coroutineScope.launch {
                        viewModel.loadVolumes()
                    }
                })
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
        verticalItemSpacing =  dimensionResource(R.dimen.volume_list_item_spacing),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.volume_list_item_spacing)),
        contentPadding = PaddingValues(dimensionResource(R.dimen.volume_list_content_padding))
    ) {
        items(uiState.items) { book ->
            BookCard(book = book, onClick = onListItemClick)
        }
    }
}

@Composable
fun VolumeListLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = stringResource(R.string.volumes_loading),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun VolumeListError(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_padding)))
            Text(text = stringResource(R.string.volumes_loading_error))
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_padding)))
            Button(onClick = onRetry) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}
