package pl.dakil.bookshelf.ui.screens.volumelist

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.dakil.bookshelf.BookshelfApplication
import pl.dakil.bookshelf.data.repo.BookshelfRepository

class VolumeListViewModel(private val bookshelfRepository: BookshelfRepository) : ViewModel() {
    private var _uiState =
        MutableStateFlow<VolumeListUiState>(VolumeListUiState.Success(emptyList()))
    val uiState: StateFlow<VolumeListUiState>
        get() = _uiState.asStateFlow()

    var searchInput by mutableStateOf("")
        private set

    fun updateSearchInput(input: String) {
        searchInput = input
    }

    suspend fun loadVolumes() = viewModelScope.launch {
        _uiState.update { VolumeListUiState.Loading }
        _uiState.update {
            try {
                val books = bookshelfRepository.getBooks(searchInput) ?: emptyList()
                VolumeListUiState.Success(books)
            } catch (e: Exception) {
                VolumeListUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BookshelfApplication
                val repository = application.appContainer.repository
                VolumeListViewModel(repository)
            }
        }
    }
}