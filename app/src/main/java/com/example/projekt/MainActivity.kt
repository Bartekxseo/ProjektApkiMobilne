package com.example.projekt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var pushUpButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pushUpButton = findViewById<View>(R.id.pushUpButton) as Button
        pushUpButton!!.setOnClickListener {
            val menuIntent = Intent(this@MainActivity, CounterActivity::class.java)
            menuIntent.putExtra("type", 1)
            startActivity(menuIntent)
        }
    }


}