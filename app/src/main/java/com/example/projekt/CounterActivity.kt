package com.example.projekt

import DbHandler
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

var gyroscope: Gyroscope? = null
var repeatsText: TextView? = null
var exerciseText: TextView? = null
var infoText: TextView? = null
var addRepeatButton: Button? = null
var removeRepeatButton: Button? = null
var startStopButton: Button? = null
var backButton: Button? = null
var started = false;
var finished = false;
var exerciseLeftToDo = 0
var stopGyro = false
private var startStop = true
private var onPause = false
private var exerciseAmount = 0
private lateinit var dbHandler: DbHandler

private val handler: Handler = Handler()

class CounterActivity : AppCompatActivity() {
    var runnable: Runnable = object : Runnable {
        override fun run() {
            if (!startStop  && !onPause) {
                    repeatsText!!.post(Runnable {
                        repeatsText!!.text = java.lang.String.format(
                            Locale.getDefault(),
                            "%01d",
                            exerciseLeftToDo
                        )
                    })
                    infoText!!.post { infoText!!.text = "Rób brzuszki!" }
            }
            if (exerciseLeftToDo === 0) {
                startStop = true
                onPause = false
                exerciseLeftToDo = exerciseAmount
                infoText!!.post { infoText!!.text = "Przygotuj trening!" }
                repeatsText!!.post(Runnable { repeatsText!!.text = "0" })
                startStopButton!!.post(Runnable { startStopButton!!.text = "Start" })
                if(started)
                {
                    started = false
                    finished = true;
                }
            }
            if(finished)
            {
                dbHandler.addNewExercise(exerciseAmount)
                finished = false;
            }
            handler.postDelayed(this, 50)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler.post(runnable)
        setContentView(R.layout.activity_counter)

        dbHandler = DbHandler(this,null)

        exerciseText = findViewById(R.id.exerciseText)
        backButton = findViewById(R.id.backButton)
        backButton!!.setOnClickListener(View.OnClickListener {
            finish()
        })
        infoText = findViewById(R.id.infoText)
        repeatsText = findViewById(R.id.repeatsText)

        val text: String = java.lang.String.format(
            Locale.getDefault(),
            "Powtórzenia: %01d",
            exerciseAmount
        )
        exerciseText!!.text = text

        addRepeatButton = findViewById(R.id.addRepeatButton)
        addRepeatButton!!.setOnClickListener(View.OnClickListener {
            if (startStop && !onPause && exerciseAmount <= 120) {
                exerciseAmount++
                exerciseLeftToDo = exerciseAmount
                val text: String = java.lang.String.format(
                    Locale.getDefault(),
                    "Powtórzenia: %01d",
                    exerciseAmount
                )
                exerciseText!!.text = text
            }
        })

        removeRepeatButton = findViewById(R.id.removeRepeatButton)
        removeRepeatButton!!.setOnClickListener(View.OnClickListener {
            if (startStop && !onPause && exerciseAmount > 0) {
                exerciseAmount--
                exerciseLeftToDo = exerciseAmount
                val text = java.lang.String.format(
                    Locale.getDefault(),
                    "Powtórzenia: %01d",
                    exerciseAmount
                )
                exerciseText!!.text = text
            }
        })

        startStopButton = findViewById(R.id.startStopButton)
        startStopButton!!.setOnClickListener(View.OnClickListener {
            if (startStop || onPause) {
                startStop = false
                onPause = false
                startStopButton!!.text = "Stop"
                started = true;
            } else if (!onPause) {
                onPause = true
                startStopButton!!.text = "Start"
            }
        })




        gyroscope = Gyroscope(this);
        gyroscope!!.setListener(object : Gyroscope.Listener {
            override fun onRotation(rx: Float, ry: Float, rz: Float) {
                if (rx < -0.3f && !stopGyro && !onPause && !startStop) {
                    exerciseLeftToDo--
                    stopGyro = true
                }
                if (rx > 0.3f && !onPause && !startStop) {
                    stopGyro = false
                }
            }
        })
    }
    override fun onResume()
    {
        super.onResume()
        gyroscope!!.register();
    }

    override fun onPause() {
        super.onPause()
        gyroscope!!.unregister();
    }
}