package com.sevenpixel.enums

object AnimalType {
    enum class AnimalTypeEnum {
        DOG,
        CAT;
    }

    fun getEnumFromString(value: String): AnimalTypeEnum? {
        return when (value) {
            AnimalTypeEnum.CAT.toString() -> AnimalTypeEnum.CAT
            AnimalTypeEnum.DOG.toString() -> AnimalTypeEnum.DOG
            else -> null
        }
    }
}