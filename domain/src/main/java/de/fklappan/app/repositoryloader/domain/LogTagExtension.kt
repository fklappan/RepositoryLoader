package de.fklappan.app.repositoryloader.domain

/**
 * Extension for any class to be able to simply call "LOG_TAG" to get an logging tag for the class.
 */
val Any.LOG_TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23 ) tag else tag.substring(0, 23)
    }