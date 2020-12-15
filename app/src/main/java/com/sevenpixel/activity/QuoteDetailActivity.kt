package com.sevenpixel.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sevenpixel.R
import com.sevenpixel.domain_obj.NetworkFile
import com.sevenpixel.enums.AnimalType
import com.sevenpixel.utils.Utility
import com.sevenpixel.utils.network_connection.NetworkRequest
import com.sevenpixel.utils.network_connection.Urls
import com.sevenpixel.utils.shared_pref.SharedPref
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_quote_detail.*

class QuoteDetailActivity : AppCompatActivity() {

    lateinit var url: String

    companion object{
        const val QUOTE_ID = "QUOTE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_detail)

        quoteText.text = intent.getStringExtra(QUOTE_ID)

        url = if (SharedPref.animalSelected == AnimalType.AnimalTypeEnum.CAT)
            Urls.randomCat
        else
            Urls.randomDog

        Utility.startNetworkIntent(
            activity = this,
            url = url,
            applicationContext = applicationContext
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(Log.DEBUG.toString(), "QuoteDetail - onActivityResult: $requestCode - $resultCode")
        if (requestCode == 0 && data != null) {
            val responseIntent = data.getByteArrayExtra(NetworkRequest.RESPONSE)
            if(responseIntent != null) {
                val nwkFile = NetworkFile.Deserializer().deserialize(String(responseIntent))
                if (nwkFile != null) {
                    if(nwkFile.urlImage.endsWith(".mp4") || nwkFile.urlImage.endsWith(".webm")){
                        Utility.startNetworkIntent(
                            activity = this,
                            applicationContext = applicationContext,
                            url = url
                        )
                    } else {
                        Picasso.get().load(nwkFile.urlImage).into(imageView)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}