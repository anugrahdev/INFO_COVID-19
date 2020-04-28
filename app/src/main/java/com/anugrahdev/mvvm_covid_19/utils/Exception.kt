package com.anugrahdev.mvvm_covid_19.utils

import java.io.IOException

class ApiException(message:String) : IOException(message)
class NoInternetException(message: String):IOException(message)
class NoConnectivityException():IOException()