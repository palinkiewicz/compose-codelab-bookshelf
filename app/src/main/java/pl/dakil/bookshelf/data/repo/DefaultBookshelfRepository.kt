package pl.dakil.bookshelf.data.repo

import pl.dakil.bookshelf.data.model.Book
import pl.dakil.bookshelf.data.network.BookshelfApiService

class DefaultBookshelfRepository(
    private val apiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getBooks(q: String): List<Book>? {
        return try {
            val response = apiService.getBooks(q)

            if (response.isSuccessful) {
                response.body()?.items ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getBook(id: String): Book? {
        return try {
            val response = apiService.getBook(id)

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}