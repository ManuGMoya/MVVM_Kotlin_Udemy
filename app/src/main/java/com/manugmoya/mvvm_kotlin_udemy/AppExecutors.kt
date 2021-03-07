package com.manugmoya.mvvm_kotlin_udemy

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AppExecutors(
    val diskIO: Executor,
    val networkIO: Executor,
    val mainThread: Executor
) {
    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    fun diskIO(): Executor {
        return this.diskIO
    }

    fun networkIO(): Executor {
        return this.networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}