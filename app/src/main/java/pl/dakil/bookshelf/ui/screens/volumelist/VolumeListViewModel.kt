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
import kotlinx.coroutines.launch
import pl.dakil.bookshelf.BookshelfApplication
import pl.dakil.bookshelf.data.repo.BookshelfRepository

class VolumeListViewModel(private val bookshelfRepository: BookshelfRepository) : ViewModel() {
    private var _uiState =
        MutableStateFlow<VolumeListUiState>(INIT_UI_STATE)
    val uiState: StateFlow<VolumeListUiState>
        get() = _uiState.asStateFlow()

    var searchInput by mutableStateOf(INIT_SEARCH_INPUT)
        private set

    fun updateSearchInput(input: String) {
        searchInput = input
    }

    suspend fun loadVolumes(query: String? = null) = viewModelScope.launch {
        _uiState.value = VolumeListUiState.Loading
        _uiState.value = try {
            val books = bookshelfRepository.getBooks(query ?: searchInput) ?: throw Exception()
            VolumeListUiState.Success(books)
        } catch (e: Exception) {
            VolumeListUiState.Error
        }
    }

    companion object {
        val INIT_UI_STATE = VolumeListUiState.Success(emptyList())
        const val INIT_SEARCH_INPUT = ""

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BookshelfApplication
                val repository = application.appContainer.repository
                VolumeListViewModel(repository)
            }
        }
    }
}