package com.example.multiclone

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val textView = TextView(this).apply {
            text = "App is running!"
            textSize = 24f
            setPadding(32, 32, 32, 32)
        }
        setContentView(textView)
    }
}
