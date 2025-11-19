package com.spordestek.app

import android.app.Application

class SporDestekApplication : Application() {
    companion object {
        lateinit var instance: SporDestekApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
