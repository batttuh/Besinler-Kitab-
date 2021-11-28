package com.batuhan.besinler_kitab.modelview

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batuhan.besinler_kitab.database.BesinDAO
import com.batuhan.besinler_kitab.database.besinDataBase
import com.batuhan.besinler_kitab.model.BesinList
import com.batuhan.besinler_kitab.servis.BesinAPIServis
import com.batuhan.besinler_kitab.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BesinListesiModelView(application:Application): BaseViewModel(application) {
    val besinler = MutableLiveData<List<BesinList>>()
    val RefreshText = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()
    private var guncellemeZamani=10*60*1000*1000*1000L
    val besinApiServis = BesinAPIServis()
    val disposable = CompositeDisposable()
    private val ozelSharedPreferences=OzelSharedPreferences(getApplication())
    fun refreshData() {
        val kaydedilmeZamani=ozelSharedPreferences.zamaniAl()
        if(kaydedilmeZamani!=null &&kaydedilmeZamani!=0L&&System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
            //sqlite
            verileriSqLittanAl()
        }else{
            download()
        }
    }
    fun refreshFromInternet(){
        download()
    }
    private fun verileriSqLittanAl(){
        progressBar.value=true
        launch {
            val besinListe=besinDataBase(getApplication()).BesinDao().getAll()
            besinleriGoster(besinListe)
            Toast.makeText(getApplication(),"Besinleri Roomdan aldık",Toast.LENGTH_LONG).show()

        }
    }
    fun download() {
        disposable.add(
            besinApiServis.getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<BesinList>>() {
                    override fun onSuccess(t: List<BesinList>) {
                        sqliteSave(t)
                        Toast.makeText(getApplication(),"Besinleri İnternetten aldık",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        progressBar.value = false
                        RefreshText.value = true
                    }
                }
                )
        )
    }

    private fun besinleriGoster(BesinList: List<BesinList>) {
        besinler.value = BesinList
        RefreshText.value = false
        progressBar.value = false

    }

    private fun sqliteSave(BesinList: List<BesinList>) {
        launch {
            val dao = besinDataBase(getApplication()).BesinDao()
            val uuidList = dao.insertAll(*BesinList.toTypedArray())
            var i = 0
            while (i < BesinList.size) {
                BesinList[i].uuid = uuidList[i].toInt()
                i++

            }
            besinleriGoster(BesinList)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())


    }
}