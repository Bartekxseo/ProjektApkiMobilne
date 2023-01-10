package com.example.projekt

import DbHandler
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var pushUpButton: Button
    private lateinit var databaseButton: Button
    private lateinit var targetButton: Button
    private lateinit var dbHandler: DbHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DbHandler(this,null)
        pushUpButton = findViewById(R.id.crunchesButton)
        pushUpButton.setOnClickListener {
            val menuIntent = Intent(this@MainActivity, CounterActivity::class.java)
            startActivity(menuIntent)
        }
        databaseButton = findViewById(R.id.databaseButton)
        databaseButton.setOnClickListener {
            val databaseIntent = Intent(this@MainActivity, DatabaseActivity::class.java )
            startActivity(databaseIntent)
        }

        targetButton = findViewById(R.id.targetButtonM)
        targetButton.setOnClickListener{
            val targetIntent = Intent(this@MainActivity, TargetActivity::class.java)
            startActivity(targetIntent)
        }

    }


}