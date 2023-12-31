package pl.dakil.bookshelf

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import pl.dakil.bookshelf.data.repo.DefaultBookshelfRepository
import pl.dakil.bookshelf.fake.FakeBookshelfApiService
import pl.dakil.bookshelf.fake.FakeVolumesDataSource
import pl.dakil.bookshelf.rules.TestDispatcherRule
import pl.dakil.bookshelf.ui.screens.volumelist.VolumeListUiState
import pl.dakil.bookshelf.ui.screens.volumelist.VolumeListViewModel

class VolumeListViewModelTest {
    private lateinit var viewModel: VolumeListViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun initializeVolumeListViewModel() {
        val apiService = FakeBookshelfApiService()
        val repository = DefaultBookshelfRepository(apiService)
        viewModel = VolumeListViewModel(repository)
    }

    @Test
    fun volumeListViewModel_initialize_storesCorrectValues() {
        val expectedUiState = VolumeListViewModel.INIT_UI_STATE
        val expectedSearchInput = VolumeListViewModel.INIT_SEARCH_INPUT

        assertEquals(expectedUiState, viewModel.uiState.value)
        assertEquals(expectedSearchInput, viewModel.searchInput)
    }

    @Test
    fun volumeListViewModel_updateSearchInput_updatesValue() {
        val newValue = "ABC"

        viewModel.updateSearchInput(newValue)

        assertEquals(newValue, viewModel.searchInput)
    }

    @Test
    fun volumeListViewModel_updateSearchInputAndLoadVolumes_updatesUiState() = runTest {
        val search = "ABC"
        val expectedUiStateOnCall = VolumeListUiState.Loading
        val expectedVolumes = FakeVolumesDataSource.volumes.filter { it.volumeInfo.title.contains(search) }
        val expectedUiStateOnFinish = VolumeListUiState.Success(expectedVolumes)

        viewModel.updateSearchInput(search)
        viewModel.loadVolumes()
        assertEquals(expectedUiStateOnCall, viewModel.uiState.value)

        testScheduler.advanceUntilIdle()
        assertEquals(expectedUiStateOnFinish, viewModel.uiState.value)
    }

    @Test
    fun volumeListViewModel_updateSearchInputAndLoadVolumesError_updatesUiState() = runTest {
        val search = ""
        val expectedUiStateOnCall = VolumeListUiState.Loading
        val expectedUiStateOnFinish = VolumeListUiState.Error

        viewModel.updateSearchInput(search)
        viewModel.loadVolumes()
        assertEquals(expectedUiStateOnCall, viewModel.uiState.value)

        testScheduler.advanceUntilIdle()
        assertEquals(expectedUiStateOnFinish, viewModel.uiState.value)
    }

    @Test
    fun volumeListViewModel_loadVolumesFromProvidedSearch_updatesUiState() = runTest {
        val search = "ABC"
        val expectedUiStateOnCall = VolumeListUiState.Loading
        val expectedVolumes = FakeVolumesDataSource.volumes.filter { it.volumeInfo.title.contains(search) }
        val expectedUiStateOnFinish = VolumeListUiState.Success(expectedVolumes)

        viewModel.loadVolumes(search)
        assertEquals(expectedUiStateOnCall, viewModel.uiState.value)

        testScheduler.advanceUntilIdle()
        assertEquals(expectedUiStateOnFinish, viewModel.uiState.value)
    }

    @Test
    fun volumeListViewModel_loadVolumesFromProvidedSearchError_updatesUiState() = runTest {
        val search = ""
        val expectedUiStateOnCall = VolumeListUiState.Loading
        val expectedUiStateOnFinish = VolumeListUiState.Error

        viewModel.loadVolumes(search)
        assertEquals(expectedUiStateOnCall, viewModel.uiState.value)

        testScheduler.advanceUntilIdle()
        assertEquals(expectedUiStateOnFinish, viewModel.uiState.value)
    }
}