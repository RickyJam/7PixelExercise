package com.sevenpixel

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.sevenpixel.activity.QuoteDetailActivity
import com.sevenpixel.domain_obj.NetworkQuote
import com.sevenpixel.enums.AnimalType
import com.sevenpixel.utils.Utility
import com.sevenpixel.utils.network_connection.NetworkRequest
import com.sevenpixel.utils.network_connection.Urls
import com.sevenpixel.utils.shared_pref.SharedPref
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_quote_detail.*
import java.util.*


class HomeActivity : AppCompatActivity() {

    private lateinit var quotesListView: ListView
    private lateinit var quotesList: List<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        SharedPref.sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        SharedPref.initOnStartUp()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quotesListView = findViewById(R.id.quotesList)

        if(SharedPref.animalSelected == null){
            showAnimalSelectionDialog()
        } else {
            homeTitle.text = getString(SharedPref.animalSelected!!.catText)
        }

        Utility.startNetworkIntent(
            activity = this,
            applicationContext = applicationContext,
            url = Urls.substituteQuotesUrl
            //url = Urls.serverUrl
        )

        FABChangeAnimal.setOnClickListener {
            showAnimalSelectionDialog()
        }
        FABChangeAnimal.setImageIcon(Icon.createWithResource(applicationContext, R.drawable.ic_icons8_edit).setTint(getColor((R.color.white))))

        quotesListView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(applicationContext,
                QuoteDetailActivity::class.java)
            intent.putExtra(QuoteDetailActivity.QUOTE_ID, quotesList[position])
            startActivity(intent)
            return@setOnItemClickListener
        }
    }

    private fun showAnimalSelectionDialog() {
        val dialog: android.app.AlertDialog = android.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_activity_select_animal_dialog))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.cat_button_text)) { _, _ ->
                SharedPref.animalSelected = AnimalType.AnimalTypeEnum.CAT
                SharedPref.storeSelectedAnimal()
                homeTitle.text = getString(SharedPref.animalSelected!!.catText)
            }
            .setNegativeButton(getString(R.string.dog_button_text)) { _, _ ->
                SharedPref.animalSelected = AnimalType.AnimalTypeEnum.DOG
                SharedPref.storeSelectedAnimal()
                homeTitle.text = getString(SharedPref.animalSelected!!.catText)
            }.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(Log.DEBUG.toString(), "onActivityResult  $requestCode  $resultCode")
        if (requestCode == 0 && data != null) {
            val responseIntent = data.getByteArrayExtra(NetworkRequest.RESPONSE)
            if(responseIntent != null) {
                quotesList = NetworkQuote.DeserializerList().deserialize(String(responseIntent))!!
                initList()
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun initList() {
        if(quotesList.size > 30)
            quotesList = quotesList.subList(0,30)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, quotesList)
        quotesListView.adapter = adapter
    }


}
