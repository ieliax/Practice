package com.example.baseapp.dependency_factory


import com.example.baseapp.weather_main.WeatherMainModel

enum class weatherRepositoryType{
    MAIN_MODEL_WEATHER
}

class DependencyFactory:IDependencyFactory<weatherRepositoryType> {

    override fun weatherRepository(type:weatherRepositoryType):Any {
        when(type.ordinal){

            0 -> {return WeatherMainModel()}

            else ->{return Any()
            }
        }
    }
}