package com.example.baseapp

import java.util.regex.Matcher
import java.util.regex.Pattern

object ValidationUtil {


    /**
     * the input not is valid if...
     * ...cityname is empty
     * ...cityname containsNumber
     */
    fun validateSearchInput(cityname:String):Map<String,Any>{

        if (cityname == ""){
            return mapOf("value" to false,"alertMessage" to "the search bar is empty please")
        }

        if (containsNumber(cityname) == true){
            return mapOf("value" to false,"alertMessage" to "the search bar containd number,just letter")
        }
        if (containsNumber(cityname) == false){
            return mapOf("value" to true,"alertMessage" to "valid")
        }

        return mapOf("value" to true,"alertMessage" to "")
    }

    private fun containsNumber(str: String):Boolean {
        val regex = "(.)*(\\d)(.)*"
        val pattern: Pattern = Pattern.compile(regex)
        val containsNumber: Boolean = pattern.matcher(str).matches()
        println("$containsNumber is a number")
        return containsNumber
    }


}