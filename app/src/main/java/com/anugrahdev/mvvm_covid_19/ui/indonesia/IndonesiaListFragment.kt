package com.anugrahdev.mvvm_covid_19.ui.indonesia

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anugrahdev.mvvm_covid_19.R
import com.anugrahdev.mvvm_covid_19.data.db.entities.indodata.IndoDataItem
import com.anugrahdev.mvvm_covid_19.utils.show
import com.anugrahdev.mvvmsampleapp.utils.Coroutines
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_indonesia_list.*
import kotlinx.android.synthetic.main.global_fragment.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


/**
 * A simple [Fragment] subclass.
 */
class IndonesiaListFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: IndonesiaViewModelFactory by instance()
    private lateinit var viewModel: IndonesiaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_indonesia_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,factory).get(IndonesiaViewModel::class.java)
        bindUI()

    }

    private fun bindUI()=Coroutines.main {
        progress_bar.show()
        viewModel.indodata.await().observe(viewLifecycleOwner, Observer {
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(item : List<IndonesiaListItem>){
        val mAdapter= GroupAdapter<ViewHolder>().apply {
            addAll(item)
        }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter=mAdapter
        }
    }

    private fun List<IndoDataItem>.toQuoteItem():List<IndonesiaListItem>{
        return this.map{
            IndonesiaListItem(it)
        }
    }

//




}
