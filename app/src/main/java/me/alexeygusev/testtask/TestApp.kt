package me.alexeygusev.testtask

import android.app.Application
import io.reactivex.plugins.RxJavaPlugins
import me.alexeygusev.testtask.utils.CrashesTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashesTree())
        }

        if (BuildConfig.DEBUG.not()) {
            RxJavaPlugins.setErrorHandler { error ->
                Timber.e(error, "New Rx unhandled error $error")
            }
        }

        startKoin {
            androidContext(applicationContext)
            modules(listOf(testModule)) // It would be a global val for multi-module projects
            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
        }
    }
}
