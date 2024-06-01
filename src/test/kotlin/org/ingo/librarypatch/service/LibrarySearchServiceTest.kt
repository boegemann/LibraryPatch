package org.ingo.librarypatch.service

import io.mockk.mockkClass
import org.ingo.librarypatch.model.BookCategory
import org.ingo.librarypatch.repository.BookRepo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LibrarySearchServiceTest {

    @Test
    fun `I can create a LibraryService instance`() {
        val bookRepoMock = mockkClass(BookRepo::class)
        assertNotNull(LibrarySearchService(bookRepoMock))
    }

// ----------------------------------------------------------------------------------------
// As a library user, I would like to be able to find books by my favourite author,
// so that I know if they are available in the library.
// ----------------------------------------------------------------------------------------

    @Test
    fun `I can find a list of books by author`() {
        val bookRepoMock = BookRepoMockHelper.createDefaultRepoMock()
        val librarySearchService = LibrarySearchService(bookRepoMock)

        assertEquals(0, librarySearchService.findBooksForAuthor("Thomas Mann").size)
        assertEquals(2, librarySearchService.findBooksForAuthor(" J.R.R. Tolkien ").size)

        val tolkienBooks = librarySearchService.findBooksForAuthor("J.R.R. Tolkien")
        assertEquals(2, tolkienBooks.size)
        assertEquals(2, tolkienBooks.filter { it.author == "J.R.R. Tolkien" }.size)
        assertEquals(2, tolkienBooks.filter { it.id.isNotBlank() }.size)
        assertEquals(1, tolkienBooks.filter { it.isbn == "978-0-596-52068-7" }.size)
        assertEquals(1, tolkienBooks.filter { it.title == "The Hobbit" }.size)
        assertEquals(2, tolkienBooks.filter { it.publisher == "Penguin Books" }.size)
        assertEquals(2, tolkienBooks.filter { it.bookCategory == BookCategory.NOVEL }.size)
        assertEquals(1, tolkienBooks.filter { it.borrowedBy == "" }.size)
        assertEquals(1, tolkienBooks.filter { it.borrowedBy.isNotBlank() }.size)
    }

    @Test
    fun `I need to specify an author when searching for books by author`() {
        val bookRepoMock = BookRepoMockHelper.createDefaultRepoMock()
        val librarySearchService = LibrarySearchService(bookRepoMock)

        assertThrows<IllegalArgumentException> {
            librarySearchService.findBooksForAuthor("")
        }
    }


// ----------------------------------------------------------------------------------------
// As a library user, I would like to be able to find books by title,
// so that I know if they are available in the library.
// ----------------------------------------------------------------------------------------

    @Test
    fun `I can find a list of books by title`() {
        val bookRepoMock = BookRepoMockHelper.createDefaultRepoMock()
        val librarySearchService = LibrarySearchService(bookRepoMock)

        assertEquals(1, librarySearchService.findBooksByTitle(" Lord Of The Rings ").size)
        assertEquals(0, librarySearchService.findBooksByTitle("Fifty Shades of Grey").size)
        assertEquals(0, librarySearchService.findBooksByTitle("Fifty Shades of Grey").size)

        val dictionaries = librarySearchService.findBooksByTitle("German Dictionary")
        assertEquals(2, dictionaries.size)
        assertEquals(2, dictionaries.filter { it.title == "German Dictionary" }.size)
        assertEquals(2, dictionaries.filter { it.id.isNotBlank() }.size)
        assertEquals(0, dictionaries.filter { it.author == "J.R.R. Tolkien" }.size)
        assertEquals(1, dictionaries.filter { it.publisher == "Collins" }.size)
        assertEquals(1, dictionaries.filter { it.author == "Merriam-Webster" }.size)
        assertEquals(1, dictionaries.filter { it.isbn == "978-0878898576" }.size)
        assertEquals(2, dictionaries.filter { it.bookCategory == BookCategory.REFERENCE }.size)
    }

    @Test
    fun `I need to specify a title when searching for books by title`() {
        val bookRepoMock = BookRepoMockHelper.createDefaultRepoMock()
        val librarySearchService = LibrarySearchService(bookRepoMock)

        assertThrows<IllegalArgumentException> {
            librarySearchService.findBooksByTitle("")
        }
    }

// ----------------------------------------------------------------------------------------
// As a library user, I would like to be able to find books by ISBN,
// so that I know if they are available in the library.
// ----------------------------------------------------------------------------------------

    @Test
    fun `I can find a book by it's ISBN number`() {
        val bookRepoMock = BookRepoMockHelper.createDefaultRepoMock()
        val librarySearchService = LibrarySearchService(bookRepoMock)

        assertNull(librarySearchService.findBookByIsbn("978-0-596-4444-7"))
        val theBook = librarySearchService.findBookByIsbn("978-0-596-52068-7")
        if (theBook != null) {
            with(theBook) {
                assertEquals("1", id)
                assertEquals("Lord of the Rings", title)
                assertEquals("Tolkien", author)
                assertEquals("Penguin Books", publisher)
                assertEquals(BookCategory.NOVEL, bookCategory)
                assertTrue { borrowedBy.isEmpty() }
            }
        }
    }

    @Test
    fun `I need to specify the ISBN number when searching for books by it's ISBN number`() {
        val bookRepoMock = BookRepoMockHelper.createDefaultRepoMock()
        val librarySearchService = LibrarySearchService(bookRepoMock)
        val exception = assertThrows<IllegalArgumentException> {
            assertNull(librarySearchService.findBookByIsbn(" "))
        }
        assertEquals("ISBN number has to be a non blank String for the search", exception.message)
    }

    @Test
    fun `I need to specify an ISBN number when searching for a book by isbn`() {
        val bookRepoMock = BookRepoMockHelper.createDefaultRepoMock()
        val librarySearchService = LibrarySearchService(bookRepoMock)

        assertThrows<IllegalArgumentException> {
            librarySearchService.findBooksByTitle("")
        }
    }

}