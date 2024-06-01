package org.ingo.librarypatch.service

import io.mockk.mockkClass
import org.ingo.librarypatch.repository.BookRepo
import org.ingo.librarypatch.repository.UserRepo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class StockServiceTest {

    @Test
    fun `I can create a StockService instance`() {
        val bookRepoMock = mockkClass(BookRepo::class)
        val userRepoMock = mockkClass(UserRepo::class)

        assertNotNull(StockService(bookRepoMock, userRepoMock))
    }

// ----------------------------------------------------------------------------------------
// As a library user, I would like to be able to find books by my favourite author,
// so that I know if they are available in the library.
// ----------------------------------------------------------------------------------------

    @Test
    fun `As the owner I can enquire how many books are checked out`() {
        val stockService = createMockedStockService()
        val count = stockService.getCountOfCheckedOutBooks("1")
        assertEquals(500, count)
    }

    @Test
    fun `If I am not the owner I can't enquire about stock`() {
        val stockService = createMockedStockService()
        val exception = assertThrows<IllegalArgumentException> {
            stockService.getCountOfCheckedOutBooks("2")
        }
        assertEquals("Service not available", exception.message)
    }


    /**
     * Helper method creating CheckoutService mocked with daos
     */
    private fun createMockedStockService(): StockService {
        val bookRepoMock = BookRepoMockHelper.createDefaultRepoMock()
        val userRepoMock = UserRepoMockHelper.createDefaultUserMock()

        return StockService(bookRepoMock, userRepoMock)
    }
}