package com.batuhan.besinler_kitab.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.batuhan.besinler_kitab.R
import com.batuhan.besinler_kitab.modelview.BesinDetayiModelView
import com.batuhan.besinler_kitab.util.ImageDownload
import com.batuhan.besinler_kitab.util.placeHolderCreate
import kotlinx.android.synthetic.main.fragment_besin_detayi.*


class BesinDetayiFragment : Fragment() {
    private lateinit var viewModel:BesinDetayiModelView
     var besinID=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_besin_detayi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        arguments?.let{
            besinID=BesinDetayiFragmentArgs.fromBundle(it).besinID


        }
        viewModel=ViewModelProviders.of(this).get(BesinDetayiModelView::class.java)
        viewModel.roomVerisiniAl(besinID)
        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer {
            besinAdi.text = it.besinIsim
            besinKalori.text = it.besinKalori
            besinKarbonhidrat.text = it.besinKarbonhidrat
            besinProtein.text = it.besinProtein
            besinYag.text = it.besinYag

            context?.let { context ->
                besinGorsel.ImageDownload(it.besinGorsel, placeHolderCreate(context))
            }
        })


}
    }