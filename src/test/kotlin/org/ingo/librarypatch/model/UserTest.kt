package org.ingo.librarypatch.model

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserTest {

    @Test
    fun `There are exactly two user roles - CUSTOMER and OWNER`() {
        assert(UserRole.entries.count() == 2)
        assertNotNull(UserRole.CUSTOMER)
        assertNotNull(UserRole.OWNER)
    }

    @Test
    fun `I can create a new User with an id, first and last name, email address and user role`(){
        val user = User(
            id = "12345",
            firstName = "Ingo",
            email = "ingo.boegemann@gmail.com",
            lastName = "Boegemann",
            role = UserRole.OWNER
        )
        with(user) {
            assertEquals("12345",id)
            assertEquals("Ingo",firstName)
            assertEquals("ingo.boegemann@gmail.com",email)
            assertEquals("Boegemann",lastName)
            assertEquals(UserRole.OWNER,role)
        }
    }
}