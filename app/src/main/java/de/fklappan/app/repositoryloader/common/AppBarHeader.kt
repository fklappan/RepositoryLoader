package de.fklappan.app.repositoryloader.common

/**
 * Interface to define a contract for dynamically setting the application header text.
 */
interface AppBarHeader {
    /**
     * Sets the application header text
     * @param text      the text for the app header
     */
    fun setHeaderText(text: String)

    /**
     * @see setHeaderText
     * @param resource  the Android string resource id
     */
    fun setHeaderText(resource: Int)
}