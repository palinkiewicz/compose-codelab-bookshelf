package pl.dakil.bookshelf.data.model

data class BooksQueryResponse (
    val kind: String,
    val totalItems: Int,
    val items: List<Book>
)