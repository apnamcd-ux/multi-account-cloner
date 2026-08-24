package com.example.multiclone

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private lateinit var gridLayout: GridLayout
    private var accountCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("ClonerPrefs", Context.MODE_PRIVATE)
        accountCount = prefs.getInt("AccountCount", 0)

        val mainLayout = RelativeLayout(this)

        // Header Title
        val titleText = TextView(this).apply {
            id = View.generateViewId()
            text = "Clone App"
            textSize = 24f
            setTypeface(null, Typeface.BOLD)
            setPadding(40, 40, 40, 20)
        }
        mainLayout.addView(titleText)

        // Scrollable Grid for Clones
        val scrollView = ScrollView(this).apply {
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            ).apply {
                addRule(RelativeLayout.BELOW, titleText.id)
            }
            layoutParams = params
        }

        gridLayout = GridLayout(this).apply {
            columnCount = 4
            setPadding(20, 20, 20, 20)
        }
        scrollView.addView(gridLayout)
        mainLayout.addView(scrollView)

        // Floating (+) Button to add new Tesla instance
        val fab = Button(this).apply {
            text = "+"
            textSize = 28f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#3F51B5"))

            val fabParams = RelativeLayout.LayoutParams(160, 160).apply {
                addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                addRule(RelativeLayout.ALIGN_PARENT_END)
                setMargins(0, 0, 50, 50)
            }
            layoutParams = fabParams

            setOnClickListener {
                accountCount++
                prefs.edit().putInt("AccountCount", accountCount).apply()
                renderGrid()
            }
        }
        mainLayout.addView(fab)

        setContentView(mainLayout)
        renderGrid()
    }

    private fun renderGrid() {
        gridLayout.removeAllViews()

        for (i in 1..accountCount) {
            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(20, 20, 20, 20)
            }

            // Generate an icon with the number (e.g., 1, 2, 3...)
            val iconImage = ImageView(this).apply {
                setImageDrawable(createNumberedIcon(i))
                val layoutParams = LinearLayout.LayoutParams(140, 140)
                this.layoutParams = layoutParams
            }

            val labelText = TextView(this).apply {
                text = "Tesla$i"
                textSize = 14f
                gravity = Gravity.CENTER
            }

            itemLayout.addView(iconImage)
            itemLayout.addView(labelText)

            // Click to open target web session
            itemLayout.setOnClickListener {
                val intent = Intent(this@MainActivity, WebInstanceActivity::class.java).apply {
                    putExtra("EXTRA_PROFILE_ID", "tesla_$i")
                    putExtra("EXTRA_URL", "https://web.whatsapp.com")
                }
                startActivity(intent)
            }

            // Long-press to delete the clone
            itemLayout.setOnLongClickListener {
                if (i == accountCount) {
                    accountCount--
                    getSharedPreferences("ClonerPrefs", Context.MODE_PRIVATE)
                        .edit().putInt("AccountCount", accountCount).apply()
                    renderGrid()
                    Toast.makeText(this@MainActivity, "Tesla$i deleted", Toast.LENGTH_SHORT).show()
                }
                true
            }

            gridLayout.addView(itemLayout)
        }
    }

    // Programmatically generates round numbered icons (1, 2, 3...)
    private fun createNumberedIcon(number: Int): BitmapDrawable {
        val size = 150
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val bgPaint = Paint().apply {
            color = Color.parseColor("#4CAF50") // Green icon background
            isAntiAlias = true
        }
        canvas.drawRoundRect(0f, 0f, size.toFloat(), size.toFloat(), 30f, 30f, bgPaint)

        val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = 70f
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        val xPos = canvas.width / 2f
        val yPos = (canvas.height / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f)
        canvas.drawText(number.toString(), xPos, yPos, textPaint)

        return BitmapDrawable(resources, bitmap)
    }
}
