package com.anugrahdev.mvvm_covid_19.ui.global

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.anugrahdev.mvvm_covid_19.R
import com.anugrahdev.mvvm_covid_19.databinding.GlobalFragmentBinding
import com.anugrahdev.mvvm_covid_19.utils.hide
import com.anugrahdev.mvvm_covid_19.utils.show
import com.anugrahdev.mvvmsampleapp.utils.Coroutines
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class GlobalFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory: GlobalViewModelFactory by instance()
    var country = arrayOf<String>("Seluruh Dunia")
    var js:Int =0; var jp:Int=0; var jm:Int=0

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

        binding.btnInfoPenting.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_globalFragment_to_globalCountryListFragment)
        }



        return binding.root
    }

    private fun loadData(binding:GlobalFragmentBinding){
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
                binding.spinner.setTitle("Pilih Negara")
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



