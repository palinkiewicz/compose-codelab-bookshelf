package pl.dakil.bookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pl.dakil.bookshelf.ui.BookshelfApp
import pl.dakil.bookshelf.ui.theme.BookshelfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookshelfTheme {
//                TODO: Responsiveness
                BookshelfApp()
            }
        }
    }
}