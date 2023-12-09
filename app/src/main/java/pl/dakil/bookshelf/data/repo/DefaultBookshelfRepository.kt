package pl.dakil.bookshelf.data.repo

import android.accounts.NetworkErrorException
import pl.dakil.bookshelf.data.model.Book
import pl.dakil.bookshelf.data.network.BookshelfApiService

class DefaultBookshelfRepository(
    private val apiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getBooks(q: String): List<Book>? {
        return try {
            val response = apiService.getBooks(q)

            if (response.isSuccessful) response.body()?.items ?: emptyList()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getBook(id: String): Book? {
        return try {
            val response = apiService.getBook(id)

            if (response.isSuccessful) response.body()
            else throw NetworkErrorException(response.code().toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}