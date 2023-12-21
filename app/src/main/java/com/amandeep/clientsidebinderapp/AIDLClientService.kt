package com.amandeep.clientsidebinderapp

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.amandeep.androdservicedemo.IMyAidlInterface

object AIDLClientService {

    private var remoteServiceInterface :IMyAidlInterface?=null

    val aidlServiceComponent = object :ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
             remoteServiceInterface =    IMyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    fun getRemoveService() = remoteServiceInterface
}