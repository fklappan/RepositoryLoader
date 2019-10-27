package de.fklappan.app.repositoryloader.common

import android.util.Log
import de.fklappan.app.repositoryloader.domain.Logger

/**
 * This is the Android specific implementation of the OS independent Logger interface
 */
class AndroidLoggerImpl : Logger {
    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

}