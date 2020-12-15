package com.sevenpixel.utils.network_connection

object Urls {
    const val serverUrl: String = "https://programming-quotes-api.herokuapp.com/"

    const val quotesUrl: String = serverUrl + "quotes"
    const val randomQuotesUrl: String = serverUrl + "quotes/random"

    const val substituteQuotesUrl: String = "https://raw.githubusercontent.com/skolakoda/programming-quotes-api/master/backup/quotes.json"

    const val randomCat: String = "https://aws.random.cat/meow"
    const val randomDog: String = "https://random.dog/woof.json"
}