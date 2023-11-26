package pl.dakil.bookshelf.ui.screens.volumedetails

import pl.dakil.bookshelf.data.model.Book

sealed interface VolumeDetailsUiState {
    object Loading : VolumeDetailsUiState
    object Error : VolumeDetailsUiState
    data class Success(val book: Book) : VolumeDetailsUiState
}