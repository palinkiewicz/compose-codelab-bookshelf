package pl.dakil.bookshelf.ui.screens.volumedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.dakil.bookshelf.BookshelfApplication
import pl.dakil.bookshelf.data.model.Book
import pl.dakil.bookshelf.data.repo.BookshelfRepository

class VolumeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow<VolumeDetailsUiState>(VolumeDetailsUiState.Loading)
    val uiState: StateFlow<VolumeDetailsUiState>
        get() = _uiState.asStateFlow()

    private val _bookId: String = checkNotNull(savedStateHandle["volumeId"])

    init {
        viewModelScope.launch {
            getBook()
        }
    }

    suspend fun getBook(id: String? = null) = viewModelScope.launch {
        _uiState.value = VolumeDetailsUiState.Loading
        _uiState.value = try {
            val book: Book = bookshelfRepository.getBook(id ?: _bookId) ?: throw Exception()
            VolumeDetailsUiState.Success(book = book)
        } catch (e: Exception) {
            VolumeDetailsUiState.Error
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BookshelfApplication
                val repository = application.appContainer.repository
                val savedStateHandle = this.createSavedStateHandle()
                VolumeDetailsViewModel(savedStateHandle, repository)
            }
        }
    }
}