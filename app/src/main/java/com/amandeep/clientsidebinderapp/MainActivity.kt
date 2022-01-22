package com.amandeep.clientsidebinderapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.amandeep.clientsidebinderapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  var serviceIntent: Intent?=null
    private var serviceConnection:ServiceConnection?=null
    private val TAG="MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceIntent= Intent().apply {
            component = ComponentName("com.amandeep.androdservicedemo","com.amandeep.androdservicedemo.bindingbetweentwoaaps.ServerSideService")

        }

        handlingClickEvents()

        serviceConnection=object :ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                Log.e(TAG, "onServiceConnected: ${p0?.className} p1 ${p1?.isBinderAlive}")
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.e(TAG, "onServiceDisconnected: ${p0?.className}")
            }

            override fun onBindingDied(name: ComponentName?) {
                Log.e(TAG, "onBindingDied: $name")
                super.onBindingDied(name)
            }

            override fun onNullBinding(name: ComponentName?) {
                Log.e(TAG, "onNullBinding: $name")
                super.onNullBinding(name)
            }
        }

    }

    private fun handlingClickEvents() {
        binding.btnStartService.setOnClickListener {

        }

        binding.btnStopService.setOnClickListener {

        }

        binding.btnGetRandomNumber.setOnClickListener {

        }

    }
}