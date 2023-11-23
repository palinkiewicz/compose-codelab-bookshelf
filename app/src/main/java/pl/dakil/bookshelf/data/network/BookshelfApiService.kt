package pl.dakil.bookshelf.data.network

import pl.dakil.bookshelf.data.model.Book
import pl.dakil.bookshelf.data.model.BooksQueryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("volumes")
    suspend fun getBooks(@Query("q") q: String): Response<BooksQueryResponse>

    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: String): Response<Book>

}