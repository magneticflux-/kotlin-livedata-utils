package com.github.magneticflux.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class InstantTaskExecutorExtension : BeforeAllCallback, AfterAllCallback, InstantTaskExecutorRule() {
    override fun beforeAll(context: ExtensionContext?) {
        super.starting(null)
    }

    override fun afterAll(context: ExtensionContext?) {
        super.finished(null)
    }
}