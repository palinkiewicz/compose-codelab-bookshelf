package pl.dakil.bookshelf

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dakil.bookshelf.data.repo.DefaultBookshelfRepository
import pl.dakil.bookshelf.fake.FakeBookshelfApiService
import pl.dakil.bookshelf.fake.FakeVolumesDataSource
import pl.dakil.bookshelf.rules.TestDispatcherRule
import pl.dakil.bookshelf.ui.screens.volumedetails.VolumeDetailsUiState
import pl.dakil.bookshelf.ui.screens.volumedetails.VolumeDetailsViewModel

class VolumeDetailsViewModelTest {
    private lateinit var viewModel: VolumeDetailsViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun initializeVolumeDetailsViewModel() {
        viewModel = VolumeDetailsViewModel(savedStateHandle(""), repository)
    }

    @Test
    fun volumeDetailsViewModel_initialize_storesUiStateAndUpdatesIt() = runTest {
        val id = FakeVolumesDataSource.volumes.last().id
        val currentViewModel = VolumeDetailsViewModel(savedStateHandle(id), repository)
        val expectedUiStateOnInit = VolumeDetailsUiState.Loading
        val expectedUiStateOnFinish =
            VolumeDetailsUiState.Success(FakeVolumesDataSource.volumes.first { it.id == id })

        assertEquals(expectedUiStateOnInit, currentViewModel.uiState.value)
        testScheduler.advanceUntilIdle()
        assertEquals(expectedUiStateOnFinish, currentViewModel.uiState.value)
    }

    @Test
    fun volumeDetailsViewModel_initializeError_storesUiStateAndUpdatesIt() = runTest {
        val currentViewModel = VolumeDetailsViewModel(savedStateHandle(""), repository)
        val expectedUiStateOnInit = VolumeDetailsUiState.Loading
        val expectedUiStateOnFinish = VolumeDetailsUiState.Error

        assertEquals(expectedUiStateOnInit, currentViewModel.uiState.value)
        testScheduler.advanceUntilIdle()
        assertEquals(expectedUiStateOnFinish, currentViewModel.uiState.value)
    }

    @Test(expected = IllegalStateException::class)
    fun volumeDetailsViewModel_initializeWithEmptySavedStateHandle_throwsException() = runTest {
        VolumeDetailsViewModel(emptySavedStateHandle, repository)
    }

    @Test
    fun volumeDetailsViewModel_loadBook_updatesUiState() = runTest {
        val id = FakeVolumesDataSource.volumes.last().id
        val expectedUiStateOnInit = VolumeDetailsUiState.Loading
        val expectedUiStateOnFinish =
            VolumeDetailsUiState.Success(FakeVolumesDataSource.volumes.first { it.id == id })

        viewModel.loadBook(id)
        assertEquals(expectedUiStateOnInit, viewModel.uiState.value)

        testScheduler.advanceUntilIdle()
        assertEquals(expectedUiStateOnFinish, viewModel.uiState.value)
    }

    @Test
    fun volumeDetailsViewModel_loadBookError_updatesUiState() = runTest {
        val id = ""
        val expectedUiStateOnInit = VolumeDetailsUiState.Loading
        val expectedUiStateOnFinish = VolumeDetailsUiState.Error

        viewModel.loadBook(id)
        assertEquals(expectedUiStateOnInit, viewModel.uiState.value)

        testScheduler.advanceUntilIdle()
        assertEquals(expectedUiStateOnFinish, viewModel.uiState.value)
    }

    companion object {
        private val apiService = FakeBookshelfApiService()
        private val repository = DefaultBookshelfRepository(apiService)
        private val emptySavedStateHandle = SavedStateHandle()

        private fun savedStateHandle(id: String) = SavedStateHandle(mapOf("volumeId" to id))
    }
}