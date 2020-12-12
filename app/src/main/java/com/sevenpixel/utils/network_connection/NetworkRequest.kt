package com.sevenpixel.utils.network_connection

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.github.kittinunf.fuel.core.Body
import com.github.kittinunf.fuel.core.Response
import com.google.gson.Gson


class NetworkRequest: IntentService(NetworkRequest::class.java.simpleName) {

    companion object {
        const val PENDING_RESULT_EXTRA = "pending_result"
        const val RESPONSE = "response"
        const val FETCH_URL = "fetch_url"
        const val RESULT_CODE = 0
    }

    override fun onHandleIntent(intent: Intent?) {
        if(intent?.getStringExtra(FETCH_URL) == null)
            return

        val url: String = intent.getStringExtra(FETCH_URL)!!
        val reply: PendingIntent? = intent.getParcelableExtra(PENDING_RESULT_EXTRA)
        val (_, response, _) = url.httpGet().response()
        val intentResult = Intent().putExtra(RESPONSE, response.data)
        reply?.send(this, RESULT_CODE, intentResult)
    }
}