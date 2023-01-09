package com.example.projekt

import DbHandler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DatabaseActivity : AppCompatActivity() {
    private var itemsList = ArrayList<ExerciseModel>()
    private lateinit var adapter: Adapter
    private var backButton: Button? = null
    private lateinit var dbHandler: DbHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
        dbHandler = DbHandler(this,null)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        prepareItems()
        adapter = Adapter(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        backButton = findViewById(R.id.backButton)
        backButton!!.setOnClickListener { finish() }

    }
    private fun prepareItems() {
         itemsList.addAll(dbHandler.getExercise())
    }
}