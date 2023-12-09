package pl.dakil.bookshelf.fake

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import pl.dakil.bookshelf.data.model.Book
import pl.dakil.bookshelf.data.model.BooksQueryResponse
import pl.dakil.bookshelf.data.network.BookshelfApiService
import retrofit2.Response

class FakeBookshelfApiService : BookshelfApiService {
    override suspend fun getBooks(q: String): Response<BooksQueryResponse> =
        if (q != "") {
            val volumes = FakeVolumesDataSource.volumes.filter { it.volumeInfo.title.contains(q) }
            Response.success(
                BooksQueryResponse(
                    kind = "books#volumes",
                    totalItems = volumes.size,
                    items = volumes
                )
            )
        }
        else Response.error(400, "Error".toResponseBody("text/plain".toMediaTypeOrNull()))

    override suspend fun getBook(id: String): Response<Book> =
        if (id != "")  {
            val book = FakeVolumesDataSource.volumes.first { it.id == id }
            Response.success(book)
        }
        else Response.error(400, "Error".toResponseBody("text/plain".toMediaTypeOrNull()))
}