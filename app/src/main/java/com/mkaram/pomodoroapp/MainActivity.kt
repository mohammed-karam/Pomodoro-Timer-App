package com.mkaram.pomodoroapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var recievedData: Long = 0
    var TIME_IN_MILLI: Long = 0
    var remainingTime :Long =0
    var isrunnig = false
    val REMAINING_TIME_KEY = "remaining time"
    lateinit var start: CountDownTimer
    lateinit var startBtn: Button
    lateinit var resetTv: TextView
    lateinit var timeTv: TextView
    lateinit var titleTv: TextView
    lateinit var pb: ProgressBar
    lateinit var back_btn : ImageView

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timeTv = findViewById(R.id.time_tv)
        startBtn = findViewById(R.id.start_btn)
        titleTv = findViewById(R.id.title_tv)
        resetTv = findViewById(R.id.reset_tv)
        pb = findViewById(R.id.progressBar)
        back_btn = findViewById(R.id.back_view)



        startBtn.setOnClickListener {
            if (!isrunnig)
                startTimer(TIME_IN_MILLI)
            isrunnig = true
        }

        back_btn.setOnClickListener{
            val backIntent = Intent(this,FirstActivity::class.java)
            startActivity(backIntent)
        }

        resetTv.setOnClickListener {
            restartTimer()
            isrunnig = false
        }



    }
    // this code to saves remaining time before destroying activity (before landscape)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(REMAINING_TIME_KEY,remainingTime)
    }

    // this code to restores remaining time after starting new activity (after landscape)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val startingTime=savedInstanceState.getLong(REMAINING_TIME_KEY)


        // if the timer was not running then don't start automatically
        // is this code removed the timer will start automatically when user rotates the phone
        // even it was not started by the user
        if(remainingTime!=TIME_IN_MILLI)
            startTimer(startingTime)
    }

    private fun update() {
        val minutes = remainingTime.div(1000).div(60)
        val seconds = remainingTime.div(1000) % 60
        val formatedText = String.format("%02d:%02d", minutes, seconds)
        timeTv.text = formatedText
    }

    private fun startTimer(startingTime:Long) {
        start = object : CountDownTimer(startingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                pb.progress =
                    ((remainingTime.toDouble().div(TIME_IN_MILLI.toDouble())) * 100).toInt()
                update()
                titleTv.text = resources.getText(R.string.keep_going)

            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "finish", Toast.LENGTH_SHORT).show()
                isrunnig = false
            }
        }.start()
    }

    private fun restartTimer() {
        start.cancel()
        remainingTime = TIME_IN_MILLI
        update()
        pb.progress = 100
        titleTv.text = resources.getText(R.string.take_a_pomodoro)
    }

    override fun onStart() {
        super.onStart()
        recievedData = intent.getLongExtra("key",0)
        timeTv.setText(recievedData.toString())
        TIME_IN_MILLI= recievedData * 60 * 1000
        remainingTime = TIME_IN_MILLI
    }
}