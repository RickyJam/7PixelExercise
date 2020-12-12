package com.sevenpixel.utils.shared_pref

import android.content.SharedPreferences
import android.util.Log
import com.sevenpixel.enums.AnimalType
import com.sevenpixel.enums.AnimalType.AnimalTypeEnum
import com.sevenpixel.enums.SharedPrefKeyEnum

object SharedPref {

    var sharedPreferences: SharedPreferences? = null
    var animalSelected: AnimalTypeEnum? = null

    fun initOnStartUp() {
        Log.i(Log.DEBUG.toString(), "SharedPref: initOnStartUp() - Started")

        animalSelected = readAnimalSelected()

        Log.i(Log.DEBUG.toString(), "SharedPref: initOnStartUp() - Ended")
    }

    fun storeSelectedAnimal(selectedAnimal: AnimalTypeEnum? = this.animalSelected ) {
        Log.i(Log.DEBUG.toString(), "SharedPref: storeSelectedAnimal() - Started")
        if(selectedAnimal == null)
            return

        with(sharedPreferences?.edit()) {
            this?.putString(SharedPrefKeyEnum.SELECTED_ANIMAL.toString(), selectedAnimal.toString())
            this?.apply()
        }

        Log.i(Log.DEBUG.toString(), "SharedPref: storeSelectedAnimal() - Ended")
    }

    private fun readAnimalSelected(): AnimalTypeEnum? {
        Log.i(Log.DEBUG.toString(), "SharedPref: readAnimalSelected() - Started")

        val animalStored: String? = sharedPreferences?.getString(SharedPrefKeyEnum.SELECTED_ANIMAL.toString(), null)

        return if (animalStored.isNullOrEmpty()) {
            Log.i(Log.DEBUG.toString(),"SharedPref: readAnimalSelected() - Ended - Animal not selected yet")
            null
        } else {
            Log.i(Log.DEBUG.toString(),"SharedPref: readAnimalSelected() - Ended")
            AnimalType.getEnumFromString(animalStored)
        }
    }

}