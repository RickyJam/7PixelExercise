package com.sevenpixel.enums

import com.sevenpixel.R

object AnimalType {
    enum class AnimalTypeEnum(val catText: Int) {
        DOG (R.string.cat_button_text),
        CAT (R.string.dog_button_text);
    }

    fun getEnumFromString(value: String): AnimalTypeEnum? {
        return when (value) {
            AnimalTypeEnum.CAT.toString() -> AnimalTypeEnum.CAT
            AnimalTypeEnum.DOG.toString() -> AnimalTypeEnum.DOG
            else -> null
        }
    }
}