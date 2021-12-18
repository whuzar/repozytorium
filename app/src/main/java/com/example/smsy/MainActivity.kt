package com.example.tutorialspoint
import android.R
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : Activity(){
    var  sendBtn: android.widget.Button? = null
    var  txtphoneNo: EditText? = null
    var  txtMessage: EditText? = null
    var  phoneNo: kotlin.String? = null
    var  message: kotlin.String? = null
    override  fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendBtn = findViewById<android.view.View>(R.id.btnSendSMS) as android.widget.Button
        txtphoneNo = findViewById<android.view.View>(R.id.editText) as EditText
        txtMessage = findViewById<android.view.View>(R.id.editText2) as EditText
        sendBtn!!.setOnClickListener { sendSMSMessage() }
    }
    protected   fun sendSMSMessage(){
        phoneNo = txtphoneNo!!.text.toString()
        message = txtMessage!!.text.toString()
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.SEND_SMS)){} else {
                ActivityCompat.requestPermissions(this, arrayOf<kotlin.String>(android.Manifest.permission.SEND_SMS),
                    MainActivity.Companion.MY_PERMISSIONS_REQUEST_SEND_SMS)
            }
        }
    }
    override  fun onRequestPermissionsResult(requestCode: kotlin.Int, permissions: kotlin.Array<kotlin.String>, grantResults: kotlin.IntArray){
        when(requestCode){MainActivity.Companion.MY_PERMISSIONS_REQUEST_SEND_SMS -> {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ){
                val smsManager = android.telephony.SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNo, null, message, null, null)
                Toast.makeText(
                    applicationContext, "SMS sent.",
                    Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "SMS faild, please try again.", Toast.LENGTH_LONG).show()
                return
            }
        }}
    }
    companion object  {
        private const val  MY_PERMISSIONS_REQUEST_SEND_SMS = 0
    }
}