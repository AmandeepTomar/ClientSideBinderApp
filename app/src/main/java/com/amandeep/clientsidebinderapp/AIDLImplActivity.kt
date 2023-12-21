package com.amandeep.clientsidebinderapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amandeep.androdservicedemo.IMyAidlInterface
import com.amandeep.clientsidebinderapp.databinding.ActivityAidlimplBinding


class AIDLImplActivity : AppCompatActivity() {

    private var _binding:ActivityAidlimplBinding?=null

    private var serviceConnection:ServiceConnection?=null
    private var remoteServiceInterface : IMyAidlInterface?=null

    private val binnding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityAidlimplBinding.inflate(layoutInflater)
        setContentView(binnding?.root)
        val intent = Intent("AIDLService")
        intent.setPackage("com.amandeep.androdservicedemo")

        serviceConnection = object :ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                remoteServiceInterface = IMyAidlInterface.Stub.asInterface(service)
                Log.d("TAG", "Remote config Service Connected!! $remoteServiceInterface");
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.e("TAG", "onServiceDisconnected: ", )
            }
        }





        binnding?.btnBindAIDLService?.setOnClickListener {
            serviceConnection?.let {
                bindService(intent, it, BIND_AUTO_CREATE)
            }

        }

        binnding?.btnBindAIDLGetData?.setOnClickListener {
           val message =  remoteServiceInterface?.messge.orEmpty()
            Log.e("TAG", "onCreate: $message", )
        }


    }
}