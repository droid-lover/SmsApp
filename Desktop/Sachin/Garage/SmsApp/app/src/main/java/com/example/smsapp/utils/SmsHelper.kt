package com.example.smsapp.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import com.example.smsapp.models.Msg

class SmsHelper(private val contentResolver: ContentResolver) {
    private val INBOX_URI = "content://sms/inbox"
    private val TAG = SmsHelper::class.simpleName
    var reqCols = arrayOf("_id", "address", "body")


    fun getAllBankingSms(): ArrayList<Msg> {
        val mSmsQueryUri = Uri.parse(INBOX_URI)
        val messages: ArrayList<Msg> = ArrayList()
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(mSmsQueryUri, null, null, null, null)
            if (cursor == null) {
                Log.i(TAG, "cursor is null. uri: $mSmsQueryUri")
            } else {
                if (cursor?.moveToFirst()) {
                    do {
                        val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                        val body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                        if (body.contains("debited") || body.contains("credited")) {
                            messages.add(Msg(address, body))
                        }
                    } while (cursor.moveToNext())
                }
                cursor?.close()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        } finally {
            cursor?.close()
        }
        return messages
    }

}