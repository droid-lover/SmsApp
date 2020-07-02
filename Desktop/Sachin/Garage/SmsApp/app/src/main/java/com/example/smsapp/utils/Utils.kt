package com.example.smsapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

/**
 * Created by Sachin
 */
object Utils {


    fun showPermissionDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Allow SMSApp to reas messages from Inbox")
        val strMessage = "This lets us use your inbox to read messages"
        builder.setMessage(strMessage)
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}