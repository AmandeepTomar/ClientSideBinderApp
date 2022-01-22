package com.amandeep.clientsidebinderapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ClintBindService : Service() {
    private val TAG="ClientBindService"

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: ")
        return START_STICKY
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.i(TAG, "onRebind: ")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onLowMemory() {
        Log.i(TAG, "onLowMemory: ")
        super.onLowMemory()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: ")
        super.onDestroy()
    }

}