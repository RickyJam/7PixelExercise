package com.sevenpixel.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.sevenpixel.R
import com.sevenpixel.enums.AnimalType.AnimalTypeEnum
import com.sevenpixel.utils.shared_pref.SharedPref
import kotlinx.android.synthetic.main.content_select_animal_dialog.*


class SelectAnimalDialog(context: Context) : Dialog(context) {

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_animal_dialog)

        cat_button.setOnClickListener {
            dog_button.isClickable = false
            SharedPref.storeSelectedAnimal(selectedAnimal = AnimalTypeEnum.CAT)
            dismiss()
        }

        dog_button.setOnClickListener{
            cat_button.isClickable = false
            SharedPref.storeSelectedAnimal(selectedAnimal = AnimalTypeEnum.DOG)
            dismiss()
        }
    }

}
