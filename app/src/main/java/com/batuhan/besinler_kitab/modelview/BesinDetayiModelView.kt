package com.batuhan.besinler_kitab.modelview

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.batuhan.besinler_kitab.database.besinDataBase
import com.batuhan.besinler_kitab.model.BesinList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BesinDetayiModelView(application: Application):BaseViewModel(application) {
    val besinLiveData=MutableLiveData<BesinList>()
    fun roomVerisiniAl(uuid:Int){
        launch{
            val dao=besinDataBase(getApplication()).BesinDao()
            val besin=dao.getID(uuid)
            besinLiveData.value=besin
        }

    }
}