package com.example.projekt

import DbHandler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*

class TargetActivity : AppCompatActivity() {
    private lateinit var addTargetButton: Button
    private lateinit var removeTargetButton: Button
    private lateinit var saveButton: Button
    private lateinit var backButton: Button

    private lateinit var targetText: TextView
    private lateinit var targetEditText: TextView

    private var target = 0
    private var doneToday = 0
    private lateinit var dbHandler: DbHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)

        dbHandler = DbHandler(this,null);
        target = dbHandler.getTarget()
        doneToday = dbHandler.getTodaysExerciseDoneSum()

        targetText = findViewById(R.id.targetText)
        targetEditText = findViewById(R.id.targetEditText)

        addTargetButton = findViewById(R.id.addTargetButton)
        addTargetButton.setOnClickListener(View.OnClickListener{
            if(target<=120)
            {
                target++
                val text: String = java.lang.String.format(
                    Locale.getDefault(),
                    "Cel dzienny: %01d",
                    target
                )
                targetEditText.text = text
            }
        })

        removeTargetButton = findViewById(R.id.removeTargetButton)
        removeTargetButton.setOnClickListener(View.OnClickListener{
            if(target>0)
            {
                target--
                val text: String = java.lang.String.format(
                    Locale.getDefault(),
                    "Cel dzienny: %01d",
                    target
                )
                targetEditText.text = text
            }
        })

        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            dbHandler.updateTarget(target)
            reloadTarget()
        }

        backButton = findViewById(R.id.backButtonT)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun reloadTarget()
    {
        target = dbHandler.getTarget()
        doneToday = dbHandler.getTodaysExerciseDoneSum()
        var text : String = java.lang.String.format(
        Locale.getDefault(),
        "Cel dzienny: %01d",
        target
        )
        targetEditText.text = text
        text = java.lang.String.format(
            Locale.getDefault(),
            "Wykonanie celu dziennego: %01d/%01d",
            doneToday,target
        )
        targetText.text = text
    }

    override fun onResume() {
        super.onResume()
        reloadTarget();
    }
}