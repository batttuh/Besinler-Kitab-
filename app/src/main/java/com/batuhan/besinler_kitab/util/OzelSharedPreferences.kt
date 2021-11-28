package com.batuhan.besinler_kitab.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class OzelSharedPreferences {

    companion object{
        private val ZAMAN="zaman"
        private var lock=Any()
        private var sharedPreferences:SharedPreferences?=null
        @Volatile private var instance:OzelSharedPreferences?=null
        operator fun invoke(context: Context):OzelSharedPreferences= instance?: synchronized(lock){
            instance?: ozelSharedPreferencesYap(context).also {
                instance=it
            }
        }
        private fun ozelSharedPreferencesYap(context :Context):OzelSharedPreferences{
            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }
    }
    fun zamaniKaydet(zaman:Long){
        sharedPreferences?.edit(commit=true){
        putLong(ZAMAN,zaman)
        }
    }
    fun zamaniAl()= sharedPreferences?.getLong(ZAMAN,0)
}