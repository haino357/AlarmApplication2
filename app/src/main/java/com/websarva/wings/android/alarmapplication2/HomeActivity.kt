package com.websarva.wings.android.alarmapplication2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val am = calendar.get(Calendar.AM)
        val pm = calendar.get(Calendar.PM)
        val AMPM = calendar.get(Calendar.AM_PM)

        val S_AMPM : String
        if(AMPM != am) {
            S_AMPM = "AM"
        } else {
            S_AMPM = "PM"
        }

        val dateText = findViewById<TextView>(R.id.dateText)
        dateText.text = "${month}/${day}"

        val timeText = findViewById<TextView>(R.id.timeText)
        timeText.text = "${hour}:${minute} ${S_AMPM}"

        alarmSetbutton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
