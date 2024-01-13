package pl.dakil.bookshelf

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
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
        composeTestRule.onRoot(useUnmergedTree = true)

        composeTestRule.onNodeWithStringId(R.string.search_volumes).assertIsDisplayed()
        composeTestRule
            .onNodeWithTagForStringId(R.string.search_icon, useUnmergedTree = true)
            .assertIsDisplayed()
    }
}