package com.batuhan.besinler_kitab.servis

import com.batuhan.besinler_kitab.model.BesinList
import io.reactivex.Single
import retrofit2.http.GET

interface IBesinAPI {
    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
        @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
        fun getData():Single<List<BesinList>>




}