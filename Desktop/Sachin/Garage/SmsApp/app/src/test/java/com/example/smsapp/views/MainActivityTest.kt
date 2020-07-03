package com.example.smsapp.views


import android.content.ContentResolver
import android.net.Uri
import android.provider.ContactsContract
import com.example.smsapp.utils.SmsHelper
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.same
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.fakes.RoboCursor
/**
 * Created by Sachin
 */

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private val inboxURI = "content://sms/inbox"
    var reqCols = arrayOf("_id", "address", "body")

    private lateinit var allSmsCursor: RoboCursor
    private lateinit var contentResolver: ContentResolver

    @Before
    fun setUp() {
        val mSmsQueryUri = Uri.parse(inboxURI)
        allSmsCursor = RoboCursor()
        contentResolver = mock {
            on {
                query(
                    same(mSmsQueryUri),
                    anyOrNull(),
                    anyOrNull(),
                    anyOrNull(),
                    anyOrNull()
                )
            } doReturn allSmsCursor
        }
        allSmsCursor.setColumnNames(SMS_COLUMNS)
    }


    @Test
    fun `get full inbox list`() {
        allSmsCursor.setResults(
            arrayOf(
                SMS_1_FULL,
                SMS_2_FULL,
                SMS_3_EMPTY,
                SMS_4_EMPTY
            )
        )

        val testObserver = SmsHelper(contentResolver).getAllBankingSms()
        assert(testObserver.size>0)

    }



    companion object MockData {
        private val SMS_COLUMNS = listOf(
            "address",
            "body"
        )

        private val SMS_1_FULL = arrayOf(
            "AF_ICICI",
            "Acct XXX111 debited with INR20.00 on 02-Jul-20 and 9900867055@ybl credited." +
                    "Info:UPI-018445947777.Call 18002662 for dispute or SMS BLOCK 176 to 9215676766"
        )

        private val SMS_2_FULL = arrayOf(
            "AF_ICICI",
            "Acct XXX111 debited with INR20.00 on 02-Jul-20 and 9900867055@ybl credited." +
                    "Info:UPI-018445947777.Call 18002662 for dispute or SMS BLOCK 176 to 9215676766"
        )

        private val SMS_3_EMPTY = arrayOf(
            "",
            null
        )

        private val SMS_4_EMPTY = arrayOf(
            "",
            null
        )
    }

}