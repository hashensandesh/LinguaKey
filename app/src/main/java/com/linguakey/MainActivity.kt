package com.linguakey
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.*
class MainActivity : Activity() {
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  val box=LinearLayout(this).apply { orientation=1; setPadding(28,32,28,28); setBackgroundColor(0xFF091326.toInt()) }
  box.addView(TextView(this).apply { text="LinguaKey"; textSize=28f; setTextColor(-1) })
  box.addView(TextView(this).apply { text="Sinhala / Singlish → multilingual keyboard"; textSize=16f; setTextColor(-1) })
  box.addView(Button(this).apply { text="Enable keyboard"; setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) } })
  box.addView(Button(this).apply { text="Choose keyboard"; setOnClickListener { (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker() } })
  box.addView(TextView(this).apply { text="Test typing here"; setTextColor(-1) })
  box.addView(EditText(this).apply { hint="Type a message"; minLines=2 })
  box.addView(TextView(this).apply { text="Prototype note: translation uses sample phrases only. Live translation service is not connected yet."; setTextColor(0xFFB7C6E3.toInt()) })
  setContentView(ScrollView(this).apply { addView(box) })
 }
}
