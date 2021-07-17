package com.jay.weather

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jay.weather.api.ApiManager
import com.jay.weather.api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiManager.getApiService()
    }

    private lateinit var etInput: EditText
    private lateinit var tvOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput = findViewById(R.id.et_input)
        tvOutput = findViewById(R.id.tv_output)

        findViewById<Button>(R.id.btn_search).setOnClickListener {
            getWeather(etInput.text.toString())
        }
    }

    private fun getWeather(query: String) {
        apiService.getWeather(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                tvOutput.text = StringBuilder().apply {
                    append("temp: ${response.main.temp}\n")
                    append("name: ${response.name}\n")
                    append("country: ${response.sys.country}\n")
                }.toString()
            }
    }

}