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
import com.sevenpixel.enums.AnimalType
import com.sevenpixel.utils.Utility
import com.sevenpixel.utils.network_connection.NetworkRequest
import com.sevenpixel.utils.network_connection.Urls
import com.sevenpixel.utils.shared_pref.SharedPref
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity() {

    private lateinit var quotesListView: ListView
    private lateinit var quotesList: ArrayList<String>
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
            url = Urls.serverUrl
        )

        FABChangeAnimal.setOnClickListener {
            showAnimalSelectionDialog()
        }
        FABChangeAnimal.setImageIcon(Icon.createWithResource(applicationContext,R.drawable.ic_icons8_edit))

        quotesListView.setOnItemClickListener { _, _, position, _ ->
            Log.i(Log.DEBUG.toString(), quotesList[position])
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
            .setPositiveButton("Gatto") { _, _ ->
                SharedPref.animalSelected = AnimalType.AnimalTypeEnum.CAT
                SharedPref.storeSelectedAnimal()
                homeTitle.text = getString(SharedPref.animalSelected!!.catText)
            }
            .setNegativeButton("CANE") { _, _ ->
                SharedPref.animalSelected = AnimalType.AnimalTypeEnum.DOG
                SharedPref.storeSelectedAnimal()
                homeTitle.text = getString(SharedPref.animalSelected!!.catText)
            }.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(Log.DEBUG.toString(), "onActivityResult  $requestCode  $resultCode")
        if (requestCode == 0) {
            print(data?.getByteArrayExtra(NetworkRequest.RESPONSE)?.let { String(it) })
            debugFillQuotes()
            //quotesList = data?.getStringArrayExtra(NetworkRequest.RESPONSE)?.toList() as ArrayList  Todo enable when the services will be available
            initList()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun debugFillQuotes() {
        quotesList = ArrayList()
        quotesList.add("A brigante brigante e mezzo.")
        quotesList.add("A buon cavalier non manca lancia.")
        quotesList.add("A buon cavallo non manca sella.")
        quotesList.add("A buon cavallo non occorre dir trotta.")
        quotesList.add("A buon intenditor poche parole.")
        quotesList.add("A caldo autunno segue lungo inverno.")
        quotesList.add("A cane scottato l'acqua fredda par calda.")
        quotesList.add("A cane vecchio non dargli cuccia.")
        quotesList.add("A carnevale ogni scherzo vale, ma che sia uno scherzo che sa di sale.")
        quotesList.add("A caval che corre, non abbisognano speroni.")
        quotesList.add("A caval donato non si guarda in bocca.")
        quotesList.add("A cavalier novizio, cavallo senza vizio.")
        quotesList.add("A cavallo d'altri non si dice zoppo.")
        quotesList.add("A cavallo di fuoco, uomo di paglia, a uomo di paglia, cavallo di fuoco.")
        quotesList.add("A cavallo giovane, cavalier vecchio.")
        quotesList.add("A caval nuovo cavaliere vecchio.")
        quotesList.add("A chi batte forte, si apron le porte.")
        quotesList.add("A chi Dio vuole aiutare, niente gli può nuocere.")
    }


    private fun initList() {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, quotesList)
        quotesListView.adapter = adapter
    }


}
