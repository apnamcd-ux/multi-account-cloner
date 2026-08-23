package com.example.multiclone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var containerLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val title = TextView(this).apply {
            text = "Tesla Multi-Account Cloner"
            textSize = 20f
            setPadding(0, 0, 0, 24)
        }
        mainLayout.addView(title)

        val inputName = EditText(this).apply {
            hint = "Enter Account Name"
        }
        mainLayout.addView(inputName)

        val btnAdd = Button(this).apply {
            text = "+ Add New Instance"
            setOnClickListener {
                val label = inputName.text.toString().ifEmpty { "Account ${System.currentTimeMillis() % 1000}" }
                createCloneButton(label)
                inputName.text.clear()
            }
        }
        mainLayout.addView(btnAdd)

        val scrollView = ScrollView(this)
        containerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 24, 0, 0)
        }
        scrollView.addView(containerLayout)
        mainLayout.addView(scrollView)

        setContentView(mainLayout)
    }

    private fun createCloneButton(label: String) {
        val btnClone = Button(this).apply {
            text = "Launch: $label"
            setOnClickListener {
                val intent = Intent(this@MainActivity, WebInstanceActivity::class.java).apply {
                    putExtra("CLONE_ID", label)
                }
                startActivity(intent)
            }
        }
        containerLayout.addView(btnClone)
    }
}
