package org.ingo.librarypatch.repository

import org.ingo.librarypatch.model.User
import org.ingo.librarypatch.model.UserRole

interface UserRepo {
    /**
     * Creates a new user record in the database and returns the created User object
     */
    fun createUser(firstName: String, lastName: String, email: String, role: UserRole): User

    /**
     * Finds a user by the internal Id
     */
    fun findUserById(id: String): User

    /**
     * Checks whether the passed in id does represent a valid user
     */
    fun userIdExists(id: String): Boolean

    /**
     * Checks whether the passed in email does represent an existing user
     */
    fun userEmailExists(email: String): Boolean

    /**
     * Finds a user by the email address the user is registered in the application with
     */
    fun findUserByEmail(email: String): User
}