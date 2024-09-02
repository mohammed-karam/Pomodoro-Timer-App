package com.mkaram.pomodoroapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout

class FirstActivity : AppCompatActivity() {
    val arrlist = listOf("5", "10", "15", "20", "25", "custom")
    lateinit var et1: AutoCompleteTextView
    lateinit var et2: EditText
    lateinit var textLayout : TextInputLayout
    lateinit var btn : Button
    lateinit var  shareButton: ImageButton
    private var startValue : Long? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        btn = findViewById(R.id.button)
        shareButton = findViewById(R.id.share_button)
        val intentView = Intent(this,MainActivity::class.java)


        shareButton.setOnClickListener{
            val shareBody = "Check out this amazing app!"
            val shareSubject = "App Sharing"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val url = "https://www.example.com";
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

            // Start the share intent
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }


        val adapter = ArrayAdapter(this, R.layout.menu_item, arrlist)
        et1.setAdapter(adapter)
        et1.setOnItemClickListener { parent, view, position, id ->
            if(et1.text.toString()=="custom"){
                et2.visibility = View.VISIBLE


            }
            else{
                et2.visibility = View.INVISIBLE
                startValue = et1.text.toString().toLong()
            }
        }
        btn.setOnClickListener{
            if(et2.text.toString().isEmpty() && et1.text.toString()=="custom"){
                et2.setError("enter a custom value then click ok")

            }
            else if(et1.text.toString()!="custom"){
                startValue = et1.text.toString().toLong()
                intentView.putExtra("key",startValue)
                startActivity(intentView)
            }
            else{
                startValue = et2.text.toString().toLong()
                intentView.putExtra("key",startValue)
                startActivity(intentView)

            }

        }


    }
}