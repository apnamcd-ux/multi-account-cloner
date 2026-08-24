package com.example.multiclone

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
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
import top.niunaijun.blackbox.BlackBoxCore

class MainActivity : Activity() {

    private lateinit var gridLayout: GridLayout
    private var accountCount = 0

    // Package name of the app to clone (e.g. Tesla app)
    private val targetPackageName = "com.teslamotors.tesla"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("ClonerPrefs", Context.MODE_PRIVATE)
        accountCount = prefs.getInt("AccountCount", 0)

        val mainLayout = RelativeLayout(this)

        val titleText = TextView(this).apply {
            id = View.generateViewId()
            text = "Clone App"
            textSize = 24f
            setTypeface(null, Typeface.BOLD)
            setPadding(40, 40, 40, 20)
        }
        mainLayout.addView(titleText)

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

        // Floating '+' Button to add new cloned Tesla instance
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

                // Install package inside sandbox for the new virtual userId
                try {
                    BlackBoxCore.get().installPackageAsUser(targetPackageName, accountCount)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

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
                isClickable = true
                isFocusable = true
            }

            val iconImage = ImageView(this).apply {
                setImageDrawable(createNumberedIcon(i))
                layoutParams = LinearLayout.LayoutParams(140, 140)
            }

            val labelText = TextView(this).apply {
                text = "Tesla$i"
                textSize = 14f
                gravity = Gravity.CENTER
            }

            itemLayout.addView(iconImage)
            itemLayout.addView(labelText)

            val openAppListener = View.OnClickListener {
                Toast.makeText(this@MainActivity, "Launching Tesla$i...", Toast.LENGTH_SHORT).show()
                // Launch target APK inside virtual sandbox for profile ID = i
                try {
                    BlackBoxCore.get().launchApk(targetPackageName, i)
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Please install official Tesla app first!", Toast.LENGTH_LONG).show()
                }
            }

            itemLayout.setOnClickListener(openAppListener)
            iconImage.setOnClickListener(openAppListener)
            labelText.setOnClickListener(openAppListener)

            // Long click to remove instance
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

    private fun createNumberedIcon(number: Int): BitmapDrawable {
        val size = 150
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val bgPaint = Paint().apply {
            color = Color.parseColor("#E51937") // Tesla Red background
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
