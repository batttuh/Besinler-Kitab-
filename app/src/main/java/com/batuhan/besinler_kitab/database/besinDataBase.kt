package com.batuhan.besinler_kitab.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.batuhan.besinler_kitab.model.BesinList
import kotlinx.coroutines.internal.synchronized

@Database(entities = arrayOf(BesinList::class),version = 1)
abstract class besinDataBase:RoomDatabase() {
    abstract fun BesinDao():BesinDAO
    companion object{
        private fun createDataBase(context: Context)=
            Room.databaseBuilder(context.applicationContext,besinDataBase::class.java,"db").build()
    @Volatile private var instance:besinDataBase?=null
        private var lock=Any()
        operator fun invoke(context:Context)= instance?: kotlin.synchronized(lock){
            instance?: createDataBase(context).also {
                instance=it
            }
        }




    }
}