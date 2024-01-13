package pl.dakil.bookshelf.fake

import pl.dakil.bookshelf.data.network.BookshelfApiService
import pl.dakil.bookshelf.data.repo.BookshelfRepository
import pl.dakil.bookshelf.data.repo.DefaultBookshelfRepository
import pl.dakil.bookshelf.di.AppContainer

class FakeAppContainer : AppContainer {
    override val apiService: BookshelfApiService = FakeBookshelfApiService()
    override val repository: BookshelfRepository = DefaultBookshelfRepository(apiService)
}