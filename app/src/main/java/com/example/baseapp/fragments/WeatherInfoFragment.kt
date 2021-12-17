package com.example.baseapp.fragments

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.R
import com.example.baseapp.adapters.WeatherDailyAdapter
import com.example.baseapp.model.CurrentWeather
import com.example.baseapp.weather_main.WeatherMainView
import com.example.baseapp.weather_search.WeatherSearchView
import com.squareup.picasso.Picasso
import kotlin.math.roundToLong

class WeatherInfoFragment : Fragment() {

    var rootview:View? = null
    lateinit var icon: ImageView
    lateinit var main: TextView
    lateinit var feellike: TextView
    lateinit var min: TextView
    lateinit var max: TextView
    lateinit var search_icon: ImageView
    lateinit var city: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_weather_info, container, false)
        return rootview!!
    }

    fun init(){
        initUI()
    }


    fun initUI(){


        this.search_icon =  rootview!!.findViewById(R.id.search_iv)
        /***
         * activity method called from fragment
         */
        this.search_icon.setOnClickListener { (activity as WeatherMainView).openActivity(city.text.toString()) }



        this.city =  rootview!!.findViewById(R.id.city_tv)
        this.city.text = "Loading..."

        this.icon = rootview!!.findViewById(R.id.icon_iv)
        this.icon.alpha = 0f

        this.main = rootview!!.findViewById(R.id.main_tv)
        this.main.text = ""

        this.feellike = rootview!!.findViewById(R.id.feellike_tv)
        this.feellike.text = "0°"

        this.min = rootview!!.findViewById(R.id.mix_tv)
        this.min.text = "0°"

        this.max = rootview!!.findViewById(R.id.max_tv)
        this.max.text = "0°"

    }

    /***
     * fragment method called from activity
     */
    fun showCurrentWeather(currentWeather: CurrentWeather){
        Log.i("", currentWeather.toString()+"current weather")

            this.city.text = currentWeather.cityname

            Picasso.get().load(currentWeather.icon_url).into(this.icon)
            this.icon.alpha = 1f

            this.main.text = currentWeather.main

            startCountAnimation(feellike,currentWeather.feels_like!!)
            startCountAnimation(min,currentWeather.temp_min!!)
            startCountAnimation(max,currentWeather.temp_max!!)

    }

    private fun startCountAnimation(text:TextView,finalNumber:Int) {
        val animator = ValueAnimator.ofInt(0, finalNumber)
        animator.duration = 1000
        animator.startDelay = 0.5f.roundToLong()
        animator.addUpdateListener { animation -> text.setText(animation.animatedValue.toString()+"°") }
        animator.start()
    }
}