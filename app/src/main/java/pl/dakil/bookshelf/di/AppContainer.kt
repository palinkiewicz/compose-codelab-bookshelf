package pl.dakil.bookshelf.di

import pl.dakil.bookshelf.data.network.BookshelfApiService
import pl.dakil.bookshelf.data.repo.BookshelfRepository

interface AppContainer {
    val apiService: BookshelfApiService
    val repository: BookshelfRepository
}