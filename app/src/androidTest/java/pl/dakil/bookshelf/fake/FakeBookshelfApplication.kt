package pl.dakil.bookshelf.fake

import android.app.Application
import pl.dakil.bookshelf.di.AppContainer

class FakeBookshelfApplication : Application() {
    val appContainer: AppContainer = FakeAppContainer()
}