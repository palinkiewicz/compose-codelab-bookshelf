package pl.dakil.bookshelf

import android.app.Application
import pl.dakil.bookshelf.di.AppContainer
import pl.dakil.bookshelf.di.DefaultAppContainer

class BookshelfApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer()
    }
}