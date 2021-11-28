package com.batuhan.besinler_kitab.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.batuhan.besinler_kitab.R
import com.batuhan.besinler_kitab.adapter.RecyclerAdapter
import com.batuhan.besinler_kitab.modelview.BesinListesiModelView
import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {
    private lateinit var ViewModel:BesinListesiModelView
    private var recyclerAdapter=RecyclerAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewModel=ViewModelProviders.of(this).get(BesinListesiModelView::class.java)
        ViewModel.refreshData()
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=recyclerAdapter
        observer()
        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.visibility=View.GONE
            RefreshText.visibility=View.GONE
            progressBar.visibility=View.VISIBLE
            ViewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing=false

        }

    }
    fun observer(){
        ViewModel.besinler.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerView.visibility=View.VISIBLE
                recyclerAdapter.yeniBesinListesi(it)
            }

        })
        ViewModel.RefreshText.observe(viewLifecycleOwner, Observer {
        if(it){
            RefreshText.visibility=View.VISIBLE
            progressBar.visibility=View.GONE
            recyclerView.visibility=View.GONE

        }else{
            RefreshText.visibility=View.GONE

        }

        })
        ViewModel.progressBar.observe(viewLifecycleOwner, Observer {
            if(it){
                progressBar.visibility=View.VISIBLE
                recyclerView.visibility=View.GONE
                RefreshText.visibility=View.GONE
            }else{
                progressBar.visibility=View.GONE
            }
        })

    }
}

