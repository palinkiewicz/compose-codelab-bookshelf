package pl.dakil.bookshelf

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import pl.dakil.bookshelf.data.model.Book
import pl.dakil.bookshelf.data.repo.BookshelfRepository
import pl.dakil.bookshelf.data.repo.DefaultBookshelfRepository
import pl.dakil.bookshelf.fake.FakeBookshelfApiService
import pl.dakil.bookshelf.fake.FakeVolumesDataSource

class DefaultBookshelfRepositoryTest {
    private lateinit var repository: BookshelfRepository

    @Before
    fun initializeDefaultBookshelfRepository() {
        repository = DefaultBookshelfRepository(FakeBookshelfApiService())
    }

    @Test
    fun defaultBookshelfRepository_getBooks_returnsBooksList() = runTest {
        val search = "ABC"
        val books = repository.getBooks(search)
        val expectedBooks =
            FakeVolumesDataSource.volumes.filter { it.volumeInfo.title.contains(search) }
        assertEquals(expectedBooks, books)
    }

    @Test
    fun defaultBookshelfRepository_getBooksError_returnsNull() = runTest {
        val search = ""
        val books = repository.getBooks(search)
        val expectedBooks: List<Book>? = null
        assertEquals(expectedBooks, books)
    }

    @Test
    fun defaultBookshelfRepository_getBook_returnsBook() = runTest {
        val id = FakeVolumesDataSource.volumes.last().id
        val book = repository.getBook(id)
        val expectedBook = FakeVolumesDataSource.volumes.first { it.id == id }
        assertEquals(expectedBook, book)
    }

    @Test
    fun defaultBookshelfRepository_getBookError_returnsNull() = runTest {
        val id = ""
        val book = repository.getBook(id)
        val expectedBook: Book? = null
        assertEquals(expectedBook, book)
    }
}