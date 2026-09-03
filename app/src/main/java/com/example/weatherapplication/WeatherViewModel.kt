package com.example.weatherapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val weatherApi = WeatherApi.create()

    fun fetchWeather(city: String, apiKey: String) {
        _error.value = null
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(city, apiKey)
                _weatherData.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e.message ?: "Unknown error occurred"
                _weatherData.value = null
            }
        }
    }
}