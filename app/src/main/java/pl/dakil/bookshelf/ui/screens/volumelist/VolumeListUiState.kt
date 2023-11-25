package pl.dakil.bookshelf.ui.screens.volumelist

import pl.dakil.bookshelf.data.model.Book

sealed interface VolumeListUiState {
    object Loading : VolumeListUiState
    object Error : VolumeListUiState
    data class Success(val items: List<Book>) : VolumeListUiState
}