package com.amandeep.clientsidebinderapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amandeep.clientsidebinderapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  var serviceIntent: Intent?=null
    private var serviceConnection:ServiceConnection?=null
    private val TAG="MainActivity"
    private var isBind=false
    private val GET_RANDOM_NO_FLAG=0
    private var randomNumberValue=0
    private var count =0

    //
    private var randomNoRequestMessenger:Messenger?=null

    // used to handle the reveived message from service
    private var randomNoReceiveMessenger:Messenger?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        serviceIntent= Intent().apply {
//            component = ComponentName("com.amandeep.androdservicedemo","com.amandeep.androdservicedemo.bindingbetweentwoaaps.ServerSideService")
//
//        }

        val intent = Intent("AIDLService")
        intent.setPackage("com.amandeep.androdservicedemo")

        bindService(intent, AIDLClientService.aidlServiceComponent, BIND_AUTO_CREATE)

        handlingClickEvents()

        serviceConnection=object :ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                Log.e(TAG, "onServiceConnected: ${p0?.className} p1 ${p1?.isBinderAlive}")
                randomNoRequestMessenger= Messenger(p1)
                randomNoReceiveMessenger= Messenger(ReceiveRandomNumberHandler())
                isBind=true
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.e(TAG, "onServiceDisconnected: ${p0?.className}")
                isBind=false
                randomNoRequestMessenger=null
                randomNoReceiveMessenger=null
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
            bindServiceHere()
        }

        binding.btnStopService.setOnClickListener {
            serviceConnection?.let {
                    it1 -> unbindService(it1)
                    isBind=false;
            }
        }

        binding.btnGetRandomNumber.setOnClickListener {
                fetchRandomNumber()
        }

        binding.btnAIDLService.setOnClickListener {
         Intent(this,AIDLImplActivity::class.java).also {
             startActivity(it)
         }

        }

    }


    private fun fetchRandomNumber() {
        if (isBind){
            val requestMessage=Message.obtain(null,GET_RANDOM_NO_FLAG)
            requestMessage.replyTo=randomNoReceiveMessenger
            try {
                randomNoRequestMessenger?.send(requestMessage)
            }catch (e : RemoteException){
                Log.e(TAG, "fetchRandomNumber: $e")
            }
        }else{
            Toast.makeText(this,"Service is not bound yet",Toast.LENGTH_LONG).show()
        }

    }

    private fun bindServiceHere() {
        serviceConnection?.let { serviceIntent?.let { it1 -> bindService(it1, it,Context.BIND_AUTO_CREATE) } }
    }


    inner class ReceiveRandomNumberHandler : Handler(){

        override fun handleMessage(msg: Message) {
            randomNumberValue=0;
            when(msg.what){
                GET_RANDOM_NO_FLAG->{
                    randomNumberValue=msg.arg1
                    binding.tvClientRandomNo.text="Random Number received from Server is $randomNumberValue"
                }
            }
            super.handleMessage(msg)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isBind=false
        serviceConnection=null
    }


}