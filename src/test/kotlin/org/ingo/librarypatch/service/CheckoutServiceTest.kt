package org.ingo.librarypatch.service

import io.mockk.mockkClass
import org.ingo.librarypatch.repository.BookRepo
import org.ingo.librarypatch.repository.UserRepo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CheckoutServiceTest {

    @Test
    fun `I can create a CheckoutService instance`() {
        val bookRepoMock = mockkClass(BookRepo::class)
        val userRepoMock = mockkClass(UserRepo::class)

        assertNotNull(CheckoutService(bookRepoMock, userRepoMock))
    }


// ----------------------------------------------------------------------------------------
// As a library user, I would like to be able to borrow a book,
// so I can read it at home.
// ----------------------------------------------------------------------------------------

    @Test
    fun `I can enquire if a book is already checked out`() {
        val checkoutService = createMockedCheckoutService()
        assertTrue(checkoutService.isBookAvailableToCheckOut("978-0-596-52068-7"))
        assertFalse(checkoutService.isBookAvailableToCheckOut("999-0-596-52068-9"))
    }

    @Test
    fun `I can check out a book that is still available`() {
        val checkoutService = createMockedCheckoutService()
        checkoutService.checkoutBook("978-0-596-52068-7", "1")
    }


    @Test
    fun `I can't check out a book that is already checked out`() {
        val checkoutService = createMockedCheckoutService()

        val exception = assertThrows<IllegalArgumentException> {
            checkoutService.checkoutBook("999-0-596-52068-7", "1")
        }
        assertEquals("Book with ISBN: 999-0-596-52068-7 is already checked out", exception.message)
    }


    @Test
    fun `I can't checkout a book for a non existing user`() {
        val checkoutService = createMockedCheckoutService()
        val exception = assertThrows<IllegalArgumentException> {
            checkoutService.checkoutBook("999-0-596-52068-9", "3")
        }
        assertEquals("No such user", exception.message)
    }

    @Test
    fun `I can't checkout a book without an ISBN`() {
        val checkoutService = createMockedCheckoutService()
        val exception = assertThrows<IllegalArgumentException> {
            checkoutService.checkoutBook(" ", "1")
        }
        assertEquals("ISBN must not be blank", exception.message)
    }

    @Test
    fun `I can't checkout a book without an user id`() {
        val checkoutService = createMockedCheckoutService()
        val exception = assertThrows<IllegalArgumentException> {
            checkoutService.checkoutBook("978-0-596-52068-7", " ")
        }
        assertEquals("User id must not be blank", exception.message)
    }

    @Test
    fun `I can't checkout a book that is not available in the library`() {
        val checkoutService = createMockedCheckoutService()
        val exception = assertThrows<IllegalArgumentException> {
            checkoutService.checkoutBook("978-0-596-4444-7", "1")
        }
        assertEquals("No book with ISBN: 978-0-596-4444-7 available in the library", exception.message)
    }


// ----------------------------------------------------------------------------------------
// As a library user, I should be to prevented from borrowing reference books,
// so that they are always available.
// ----------------------------------------------------------------------------------------

    @Test
    fun `I can't check out a reference book`() {
        val checkoutService = createMockedCheckoutService()
        val exception = assertThrows<IllegalArgumentException> {
            checkoutService.checkoutBook("978-0877798576", "1")
        }
        assertEquals("Reference books can not be checked out", exception.message)
    }


    /**
     * Helper method creating CheckoutService mocked with daos
     */
    private fun createMockedCheckoutService(): CheckoutService {
        val bookRepoMock = BookRepoMockHelper.createDefaultRepoMock()
        val userRepoMock = UserRepoMockHelper.createDefaultUserMock()

        return CheckoutService(bookRepoMock, userRepoMock)
    }
}