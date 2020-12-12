package com.sevenpixel.domain_obj

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class NetworkFile(
    @SerializedName(value="urlImage", alternate=["file", "url"])
    val urlImage: String
) {
    class Deserializer: ResponseDeserializable<NetworkFile>{
        override fun deserialize(content: String): NetworkFile? = Gson().fromJson(content, NetworkFile::class.java)
    }

}