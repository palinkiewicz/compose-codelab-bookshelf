package pl.dakil.bookshelf

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dakil.bookshelf.extensions.onNodeWithStringId
import pl.dakil.bookshelf.extensions.onNodeWithTagForStringId
import pl.dakil.bookshelf.fake.FakeAppContainer
import pl.dakil.bookshelf.ui.screens.volumelist.VolumeListScreen
import pl.dakil.bookshelf.ui.screens.volumelist.VolumeListViewModel
import pl.dakil.bookshelf.ui.theme.BookshelfTheme

class VolumeListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val repository = FakeAppContainer().repository
    private val viewModel = VolumeListViewModel(repository)

    @Before
    fun initializeVolumeListScreen() {
        composeTestRule.setContent {
            BookshelfTheme {
                VolumeListScreen(
                    onListItemClick = {},
                    viewModel = viewModel
                )
            }
        }
    }

    @Test
    fun volumeListScreen_initialized_searchBarVisible() {
        // Assert the search bar is visible
        composeTestRule.onNodeWithStringId(R.string.search_volumes).assertIsDisplayed()

        // Assert the search icon is visible
        composeTestRule
            .onNodeWithTagForStringId(R.string.search_icon, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun volumeListScreen_clickedOnSearchBar_iconChangedToBackArrow() {
        // Click on the search bar
        composeTestRule.onNodeWithStringId(R.string.search_volumes).performClick()

        // Assert the back icon is visible
        composeTestRule
            .onNodeWithTagForStringId(R.string.back_icon, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    // This will not work unless other submitting method is created
    // TODO: Create a search button to make VolumeListScreen tests possible
//    @Test
//    fun volumeListScreen_searched_multipleVolumesDisplayed() = runTest {
//        val input = "ABC"
//
//        // Input text into the search bar and search
//        composeTestRule.onNodeWithStringId(R.string.search_volumes).apply {
//            performTextInput(input)
//            performImeAction()
//        }
//
//        // Assert each volume is visible
//        repository.getBooks(input)!!.forEach {
//            composeTestRule.onNodeWithText(it.volumeInfo.title).assertIsDisplayed()
//        }
//    }
}