package com.anugrahdev.mvvm_covid_19.ui.indonesia

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.anugrahdev.mvvm_covid_19.R
import com.anugrahdev.mvvm_covid_19.databinding.IndonesiaFragmentBinding
import com.anugrahdev.mvvm_covid_19.utils.hide
import com.anugrahdev.mvvm_covid_19.utils.show
import com.anugrahdev.mvvmsampleapp.utils.Coroutines
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class IndonesiaFragment : Fragment(),KodeinAware {

    override val kodein by kodein()
    private val factory: IndonesiaViewModelFactory by instance()
    var province = arrayOf<String>("Seluruh Indonesia")
    var js:String?=null; var jp:String?=null; var jm:String?=null

    companion object {
        fun newInstance() = IndonesiaFragment()
    }

    private lateinit var viewModel: IndonesiaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : IndonesiaFragmentBinding =
            DataBindingUtil.inflate(inflater,R.layout.indonesia_fragment, container,false)
        viewModel = ViewModelProviders.of(this, factory).get(IndonesiaViewModel::class.java)
        //Proses Penampilan Data
        binding.progressBar?.show()

        loadData(binding)
        binding.layoutSwipe.setOnRefreshListener {
            // Handler untuk menjalankan jeda selama 5 detik
            Handler().postDelayed({
                // Berhenti berputar/refreshing
                binding.layoutSwipe.setRefreshing(false)
                // fungsi-fungsi lain yang dijalankan saat refresh berhenti
                loadData(binding)
            }, 5000)
        }


        binding.btnIndonesiaList.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.showIndonesiaList)
        }

        return binding.root
    }

    private fun loadData(binding: IndonesiaFragmentBinding){
        province = arrayOf<String>("Seluruh Indonesia")
        Coroutines.main{
            viewModel.indosum.await().observe(viewLifecycleOwner, Observer {
                jp =  it?.positif.toString()!!
                js = it?.sembuh.toString()!!
                jm = it?.meninggal.toString()!!
            })
            viewModel.indodata.await().observe(viewLifecycleOwner, Observer {
                binding.progressBar?.hide()
                for (i in 0..it.size-1){
                    province += it.get(i).attributes.Provinsi
                }
                val arrayAdapter =
                    context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, province) }

                arrayAdapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                binding.spinner.setTitle("Pilih Provinsi")
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
                            if(province[position]=="Seluruh Indonesia"){
                                binding.meninggal?.text = jm
                                binding.positive?.text = jp
                                binding.sembuh?.text = js

                            }else if (province[position]==it.get(i).attributes.Provinsi){
                                binding.indodata = it.get(i).attributes
                            }
                        }
                    }
                }

            })
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }



}
