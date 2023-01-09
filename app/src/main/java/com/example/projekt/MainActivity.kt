package com.example.projekt

import DbHandler
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var pushUpButton: Button? = null
    var databaseButton: Button? = null
    private lateinit var dbHandler: DbHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DbHandler(this,null)
        pushUpButton = findViewById(R.id.pushUpButton)
        pushUpButton!!.setOnClickListener {
            val menuIntent = Intent(this@MainActivity, CounterActivity::class.java)
            startActivity(menuIntent)
        }
        databaseButton = findViewById(R.id.databaseButton)
        databaseButton!!.setOnClickListener {
            val databaseIntent = Intent(this@MainActivity, DatabaseActivity::class.java )
            startActivity(databaseIntent)
        }

    }


}