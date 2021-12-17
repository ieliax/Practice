package com.example.baseapp.weather_main

import android.util.Log
import android.widget.Toast
import com.example.baseapp.base.BaseContract
import com.example.baseapp.network.APIClient
import com.example.baseapp.network.APIService
import com.example.baseapp.network.response.CurrentWeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class WeatherMainModel:WeatherMainContract.Model {

    override fun requestCurrentWeather(cityname:String,onFinishedListener: BaseContract.Model.onFinishedListener<Any>){

        val url = "weather?q="+cityname+"&units=imperial&appid=4d2c49a5132f709423cd2dce0fdca8cb"

        val call = APIClient.getClient().create(APIService::class.java).requestCurrentWeather(url).enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onFailure(call: Call<CurrentWeatherResponse>?, t: Throwable?) {
                onFinishedListener.onFailure()
                Log.i("",t!!.message+"zzz")
            }

            override fun onResponse(call: Call<CurrentWeatherResponse>?, response: Response<CurrentWeatherResponse>?) {
               // data.value = response!!.body()!!.articles

                if (validation(response!!.isSuccessful,(response!!.body() != null)) == true) {
                        onFinishedListener.onSuccess(response.body()!!)
                }else{
                    Log.i("",response!!.toString()+"zzz")
                    onFinishedListener.onFailure()
                }

            }
        })

    }

    fun validation(response:Boolean,body:Boolean):Boolean{
        if (response == true && body == true){
            return true
        }
        if (response == false && body == false){
            return false
        }
        return false
    }

    override fun requestDailyWeather(cityname:String,onFinishedListener: BaseContract.Model.onFinishedListener<Any>) {

        val url = "forecast/daily?q="+cityname+"&units=imperial&cnt=7&appid=4d2c49a5132f709423cd2dce0fdca8cb"

        CoroutineScope(Dispatchers.IO).launch {

            val call = APIClient.getClient().create(APIService::class.java).requestDailyWeather(url)
            val body = call.body()
            if (call.isSuccessful){
                if (body != null){
                    onFinishedListener.onSuccess(body)
                }else{
                    onFinishedListener.onFailure()
                }
            }else{
                //
            }
        }
    }
}