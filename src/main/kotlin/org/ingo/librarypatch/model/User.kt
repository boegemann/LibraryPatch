package org.ingo.librarypatch.model

/**
 * Represents a Library user
 */
data class User(
    val id: String = "",
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: UserRole
)
