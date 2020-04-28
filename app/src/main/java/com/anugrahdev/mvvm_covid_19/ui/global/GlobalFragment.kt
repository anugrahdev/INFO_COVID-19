package com.anugrahdev.mvvm_covid_19.ui.global

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.anugrahdev.mvvm_covid_19.R
import com.anugrahdev.mvvm_covid_19.data.network.ApiServiceKC
import com.anugrahdev.mvvm_covid_19.data.repositories.GlobalDataRepository
import com.anugrahdev.mvvm_covid_19.databinding.GlobalFragmentBinding
import com.anugrahdev.mvvm_covid_19.databinding.IndonesiaFragmentBinding
import com.anugrahdev.mvvm_covid_19.ui.indonesia.IndonesiaViewModelFactory
import com.anugrahdev.mvvm_covid_19.utils.hide
import com.anugrahdev.mvvm_covid_19.utils.show
import com.anugrahdev.mvvmsampleapp.utils.Coroutines
import kotlinx.android.synthetic.main.global_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class GlobalFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory: GlobalViewModelFactory by instance()
    var country = arrayOf<String>("Seluruh Dunia")

    companion object {
        fun newInstance() = GlobalFragment()
    }

    private lateinit var viewModel: GlobalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GlobalFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.global_fragment, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(GlobalViewModel::class.java)

        binding.progressBar?.show()
        var js:Int =0; var jp:Int=0; var jm:Int=0
        Coroutines.main {
            viewModel.globaldata.await().observe(viewLifecycleOwner, Observer {
                binding.progressBar?.hide()
                for (i in 0..it.size-1){
                    country += it.get(i).attributes.Country_Region
                    js += it.get(i).attributes.Recovered
                    jp += it.get(i).attributes.Confirmed
                    jm += it.get(i).attributes.Deaths
                }
                val arrayAdapter =
                    context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, country) }

                arrayAdapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                binding.spinner?.adapter = arrayAdapter

                binding.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        for(i in 0..it.size-1){
                            if(country[position]=="Seluruh Dunia"){
                                binding.positive.text =  jp.toString()
                                binding.sembuh.text = js.toString()
                                binding.meninggal.text = jm.toString()
                            }else if (country[position]==it.get(i).attributes.Country_Region){
                                binding.globaldata = it.get(i).attributes
                            }
                        }
                    }
                }

            })
        }

        return binding.root
    }
}



