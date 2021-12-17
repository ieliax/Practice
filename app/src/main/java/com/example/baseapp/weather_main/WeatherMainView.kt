package com.example.baseapp.weather_main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.R
import com.example.baseapp.ValidationUtil
import com.example.baseapp.adapters.WeatherDailyAdapter
import com.example.baseapp.dependency_factory.DependencyFactory
import com.example.baseapp.fragments.WeatherInfoFragment
import com.example.baseapp.model.CurrentWeather
import com.example.baseapp.model.DailyWeather
import com.example.baseapp.weather_search.WeatherSearchView


class WeatherMainView : AppCompatActivity(),WeatherMainContract.View{

    lateinit var _presenter:WeatherMainContract.Presenter

    lateinit var currentfragment:WeatherInfoFragment
    lateinit var weatherDailyAdapter: WeatherDailyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_main_view)

        initUI()

        setPresenter(WeatherMainPresenter(this, DependencyFactory()))
        if (ValidationUtil.validateSearchInput("London")["value"] == true){
            _presenter.requestWeatherByName("London")
        }

    }

    override fun setPresenter(presenter: WeatherMainContract.Presenter) {
        _presenter = presenter
    }

    fun initUI(){

        /***
         * Setup fragment
         */
        currentfragment = supportFragmentManager.findFragmentById(R.id.fra) as WeatherInfoFragment
        currentfragment.init()

        /***
         * Setup Recyclerview
         */
        weatherDailyAdapter = WeatherDailyAdapter(this)
        val dailyRecyclerView = findViewById<RecyclerView>(R.id.daily_recycler_viewer)

        dailyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        dailyRecyclerView.adapter = weatherDailyAdapter

    }

    /***
     * activity method called from fragment
     */
    fun openActivity(cityname:String){
        val intent = Intent(this, WeatherSearchView::class.java)
        intent.putExtra("cityname",cityname)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1){
            val cityname = data?.getStringExtra("searchvalue")!!
            _presenter.requestWeatherByName(cityname)
        }
    }

    /***
     * fragment method called from activity
     */
    override fun showCurrentWeather(currentWeather: CurrentWeather) {
        runOnUiThread {
            currentfragment.showCurrentWeather(currentWeather)
        }
    }


    /***
     * Show data on Recyclerview
     */
    override fun showDailyWeather(weatherList: ArrayList<DailyWeather>) {
        runOnUiThread {
            weatherDailyAdapter.weatherDailyList = weatherList
            weatherDailyAdapter.notifyDataSetChanged()
        }
    }


}