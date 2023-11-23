package pl.dakil.bookshelf.data.repo

import pl.dakil.bookshelf.data.model.Book

interface BookshelfRepository {
    suspend fun getBooks(q: String): List<Book>?
    suspend fun getBook(id: String): Book?
}