package com.sevenpixel.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.sevenpixel.utils.network_connection.NetworkRequest

object Utility {
    fun startNetworkIntent(
        activity: Activity,
        applicationContext: Context,
        requestCode: Int = 0,
        url: String
    ) {
        val pendingResult = activity.createPendingResult(requestCode, Intent(), 0)
        val intent = Intent(applicationContext, NetworkRequest::class.java)
        intent.putExtra(NetworkRequest.FETCH_URL, url)
        intent.putExtra(NetworkRequest.PENDING_RESULT_EXTRA, pendingResult)
        activity.startService(intent)
    }

}