package com.example.baseapp.weather_main

import com.example.baseapp.base.BaseContract
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class WeatherMainModelTest{

    private var model:WeatherMainModel? = null

    @Before
    fun setUp(){
        model = WeatherMainModel()
    }

    @Test
    fun `API was load Successful`(){
        assertThat(model!!.validation(true,true)).isTrue()
    }

    @Test
    fun `API was load Failure`(){
        assertThat(model!!.validation(false,false)).isFalse()
    }
}
