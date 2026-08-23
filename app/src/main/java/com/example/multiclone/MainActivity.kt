package com.example.multiclone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val button = Button(this).apply {
            text = "Launch Web Instance"
            setOnClickListener {
                startActivity(Intent(this@MainActivity, WebInstanceActivity::class.java))
            }
        }
        setContentView(button)
    }
}
