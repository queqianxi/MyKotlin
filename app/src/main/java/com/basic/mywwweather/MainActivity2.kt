package com.basic.mywwweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.basic.mywwweather.ui.rengwuxian.TestAct
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        startActivity(Intent(this, TestAct::class.java))

        tvWeather.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        tvTime.setOnClickListener {
            startActivity(Intent(this, TestAct::class.java))
        }
    }
}