package org.ingo.librarypatch.model


/**
 * Represents a book available in the library
 */
data class Book(
    val id: String,
    val isbn: String,
    val title: String,
    val author: String,
    val publisher: String,
    val bookCategory: BookCategory,

    val borrowedBy: String = ""
)
