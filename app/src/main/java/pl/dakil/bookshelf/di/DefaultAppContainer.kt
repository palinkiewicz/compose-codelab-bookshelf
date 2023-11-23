package pl.dakil.bookshelf.di

import pl.dakil.bookshelf.data.network.BookshelfApiService
import pl.dakil.bookshelf.data.network.RetrofitInstance
import pl.dakil.bookshelf.data.repo.BookshelfRepository
import pl.dakil.bookshelf.data.repo.DefaultBookshelfRepository

class DefaultAppContainer : AppContainer {
    override val apiService: BookshelfApiService = RetrofitInstance.apiService
    override val repository: BookshelfRepository = DefaultBookshelfRepository(apiService)
}