package pl.dakil.bookshelf.fake

import pl.dakil.bookshelf.data.model.Book

object FakeVolumesDataSource {
    private const val NOT_FOR_SALE = "NOT_FOR_SALE"
    private const val FOR_SALE = "FOR_SALE"

    val volumes = listOf(
        Book(
            id = "0ABC",
            volumeInfo = Book.Companion.VolumeInfo(
                title = "ABC With Data",
                authors = listOf("Author 2"),
                publisher = "Publisher",
                publishedDate = "2020-01-01",
                description = "<p>Lorem ipsum...</p><p>Lorem 2</p>",
                language = "en",
                imageLinks = Book.Companion.VolumeInfo.Companion.ImageLinks(
                    thumbnail = "yellow.png",
                    smallThumbnail = "yellow.png"
                ),
                previewLink = "",
                canonicalVolumeLink = ""
            ),
            saleInfo = Book.Companion.SaleInfo(FOR_SALE)
        ),
        Book(
            id = "1ABC",
            volumeInfo = Book.Companion.VolumeInfo(
                title = "ABC",
                authors = null,
                publisher = "Pub",
                publishedDate = "2020-01-20",
                description = null,
                language = null,
                imageLinks = Book.Companion.VolumeInfo.Companion.ImageLinks(
                    thumbnail = "",
                    smallThumbnail = ""
                ),
                previewLink = "",
                canonicalVolumeLink = ""
            ),
            saleInfo = Book.Companion.SaleInfo(NOT_FOR_SALE)
        ),
        Book(
            id = "2ABC",
            volumeInfo = Book.Companion.VolumeInfo(
                title = "ABC 2",
                authors = listOf("Author 1"),
                publisher = "",
                publishedDate = null,
                description = null,
                language = null,
                imageLinks = Book.Companion.VolumeInfo.Companion.ImageLinks(
                    thumbnail = "green.png",
                    smallThumbnail = "green.png"
                ),
                previewLink = "",
                canonicalVolumeLink = ""
            ),
            saleInfo = Book.Companion.SaleInfo(NOT_FOR_SALE)
        )
    )
}