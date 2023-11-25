package pl.dakil.bookshelf.data.model

data class Book (
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo (
    val title: String,
    val authors: List<String>?,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val language: String,
    val imageLinks: ImageLinks?
)

data class ImageLinks (
    val thumbnail: String,
    val smallThumbnail: String
)