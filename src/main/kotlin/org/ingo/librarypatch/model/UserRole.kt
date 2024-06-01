package org.ingo.librarypatch.model


/**
 * Describes the role of a user in respect to the library
 * Currently we only allow for Customers and (the) Owner(s)
 */
enum class UserRole {
    CUSTOMER, OWNER
}