package com.example.baseapp


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidationUtilTest{

    @Test
    fun `empty search bar return false`(){
        val result = ValidationUtil.validateSearchInput("")
        assertThat(result["value"] as Boolean).isFalse()
    }

    @Test
    fun `search bar contains Numbers return false`(){
        val result = ValidationUtil.validateSearchInput("London123")
        assertThat(result["value"] as Boolean).isFalse()
    }

    @Test
    fun `search bar contains only letters return true`(){
        val result = ValidationUtil.validateSearchInput("London")
        assertThat(result["value"] as Boolean).isTrue()
    }



}

