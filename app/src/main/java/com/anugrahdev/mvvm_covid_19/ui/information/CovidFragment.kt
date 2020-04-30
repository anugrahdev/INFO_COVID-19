package com.anugrahdev.mvvm_covid_19.ui.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient

import com.anugrahdev.mvvm_covid_19.R

/**
 * A simple [Fragment] subclass.
 */
class CovidFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_covid, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mWebView = view?.findViewById(R.id.mywebView) as WebView
        mWebView.loadUrl("https://www.who.int/indonesia/news/novel-coronavirus/qa-for-public")
        val webSettings = mWebView.getSettings()
        webSettings.setJavaScriptEnabled(true)
        mWebView.setWebViewClient(WebViewClient())
    }

}
