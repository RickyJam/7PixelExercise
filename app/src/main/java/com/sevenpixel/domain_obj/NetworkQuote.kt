package com.sevenpixel.domain_obj

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName
import java.util.*

class NetworkQuote(
    @SerializedName(value="quote", alternate=["en"])
    val quote: String
) {
    class Deserializer: ResponseDeserializable<NetworkQuote> {
        override fun deserialize(content: String): NetworkQuote? = Gson().fromJson(content, NetworkQuote::class.java)
    }

    class DeserializerList: ResponseDeserializable<LinkedList<String>> {
        override fun deserialize(content: String): LinkedList<String>? {
            var quoteArray: LinkedList<String>? = null
            val parsedJson = JsonParser().parse(content).asJsonArray
            if(parsedJson.size() > 0) {
                quoteArray = LinkedList()
                parsedJson.forEach {
                    quoteArray.add(Gson().fromJson(it, NetworkQuote::class.java).quote)
                }
            }
            return quoteArray
        }
    }
}