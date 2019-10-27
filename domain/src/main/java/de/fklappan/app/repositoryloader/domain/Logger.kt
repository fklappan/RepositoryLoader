package de.fklappan.app.repositoryloader.domain

/**
 * Generic (OS independent) logging interface to be used within the non Android modules.
 */
interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String)
    fun w(tag: String, message: String)
}