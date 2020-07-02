package com.example.smsapp.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smsapp.R
import com.example.smsapp.adapter.MsgAdapter
import com.example.smsapp.models.Msg
import com.example.smsapp.utils.SmsHelper
import com.example.smsapp.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by Sachin
 */

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name
    private val REQUEST_READ_SMS_PERMISSION = 2000
    private val INBOX_URI = "content://sms/inbox"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkMessages()
    }

    override fun onResume() {
        super.onResume()
        if(isPermissionGranted()) getMessages()
    }
   private fun checkMessages() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isPermissionGranted()) {
                requestPermissions(arrayOf(Manifest.permission.READ_SMS), REQUEST_READ_SMS_PERMISSION)
                return
            } else {
               getMessages()
            }
        }
    }

    private fun getMessages() {
        val messages: ArrayList<Msg> = SmsHelper(contentResolver).getAllBankingSms()
//        Log.i(TAG, "Number of Messages :: " + messages.size)
        loadMessages(messages)
    }

    private fun loadMessages(messages: ArrayList<Msg>) {
        rvMsgs.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MsgAdapter(messages)
        }
    }


    private fun isPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_READ_SMS_PERMISSION -> {
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Utils.showPermissionDialog(this)
                        return
                    }
                }
            }
        }
    }
}
