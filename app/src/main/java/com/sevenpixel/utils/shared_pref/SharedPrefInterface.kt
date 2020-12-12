package com.sevenpixel.utils.shared_pref

import com.sevenpixel.enums.AnimalType

interface SharedPrefInterface {

    fun loadOnStartUp()

    fun storeSelectedAnimal(selectedAnimal: AnimalType.AnimalTypeEnum)
}
