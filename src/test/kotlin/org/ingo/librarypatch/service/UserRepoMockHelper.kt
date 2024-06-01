package org.ingo.librarypatch.service

import io.mockk.every
import io.mockk.mockkClass
import org.ingo.librarypatch.model.User
import org.ingo.librarypatch.model.UserRole
import org.ingo.librarypatch.repository.UserRepo

object UserRepoMockHelper {
    fun createDefaultUserMock(): UserRepo {
        val userRepoMock = mockkClass(UserRepo::class)

        every { userRepoMock.findUserById("1") } returns User(
            id = "1",
            lastName = "Worm",
            firstName = "Book",
            email = "book.worm@gmail.com",
            role = UserRole.OWNER
        )

        every { userRepoMock.findUserById("2") } returns User(
            id = "1",
            lastName = "Smith",
            firstName = "Fred",
            email = "fred.smith@gmail.com",
            role = UserRole.CUSTOMER
        )

        every { userRepoMock.userIdExists("1") } returns true
        every { userRepoMock.userIdExists("2") } returns true
        every { userRepoMock.userIdExists("3") } returns false

        return userRepoMock
    }
}