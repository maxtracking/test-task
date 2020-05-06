package me.alexeygusev.testtask.utils

import android.util.Log
import timber.log.Timber

class CrashesTree() : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean =
        when (priority) {
            Log.ERROR, Log.ASSERT -> true
            else -> false
        }

    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        throwable: Throwable?
    ) {
        throwable?.let {
            // report errors and asserts to your crash-reporting system of choice, eg Bugsnag or Firebase Crashlytics
        }
    }
}
