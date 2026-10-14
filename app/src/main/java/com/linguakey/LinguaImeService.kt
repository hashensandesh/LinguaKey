package com.linguakey
import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.view.View
import android.view.Gravity
import android.view.inputmethod.InputConnection
import android.content.ClipboardManager
import android.content.Context
import android.widget.*
class LinguaImeService : InputMethodService() {
 private var translateMode=true
 private var target="English"
 private lateinit var status:TextView
 private lateinit var preview:TextView
 private lateinit var pasteBox:EditText
 private fun d(n:Int)=(n*resources.displayMetrics.density).toInt()
 private fun row()=LinearLayout(this).apply { orientation=0; gravity=Gravity.CENTER }
 private fun btn(t:String, action:(Button)->Unit)=Button(this).apply { text=t; isAllCaps=false; setOnClickListener { action(this) } }
 override fun onCreateInputView():View {
  val root=LinearLayout(this).apply { orientation=1; setPadding(d(5),d(5),d(5),d(5)); setBackgroundColor(Color.rgb(9,19,38)) }
  val top=row()
  top.addView(btn("Translate Mode: ON") { b -> translateMode=!translateMode; b.text=if(translateMode)"Translate Mode: ON" else "Translate Mode: OFF"; status.text=if(translateMode)"Translate Mode is ON" else "Translate Mode is OFF" })
  top.addView(btn("To: English ▾") { b -> val opts=arrayOf("English","Italian","French","Spanish","German","Tamil","Japanese","Arabic"); android.app.AlertDialog.Builder(this).setTitle("Translate to").setItems(opts){_,i->target=opts[i];b.text="To: $target ▾";status.text="Target: $target"}.show() })
  root.addView(top)
  status=TextView(this).apply { text="Type in Sinhala/Singlish. Sample suggestions only."; setTextColor(0xFF8DE9E2.toInt()); textSize=12f; setPadding(d(6),d(4),d(6),d(4)) }; root.addView(status)
  preview=TextView(this).apply { text="Translation suggestion appears here"; setTextColor(Color.WHITE); textSize=16f; setPadding(d(8),d(8),d(8),d(8)); setBackgroundColor(0xFF17294A.toInt()) }; root.addView(preview)
  val actions=row()
  actions.addView(btn("Paste & translate") { pasteAndTranslate() })
  actions.addView(btn("Use translation") { val s=preview.text.toString(); if(!s.startsWith("Translation suggestion") && !s.startsWith("Live translation")) currentInputConnection?.commitText(s,1) })
  root.addView(actions)
  pasteBox=EditText(this).apply { hint="Paste message here"; setTextColor(Color.WHITE); setHintTextColor(Color.LTGRAY); textSize=14f; setBackgroundColor(0xFF142441.toInt()) }; root.addView(pasteBox,LinearLayout.LayoutParams(-1,d(42)))
  addKeys(root,listOf("q","w","e","r","t","y","u","i","o","p")); addKeys(root,listOf("a","s","d","f","g","h","j","k","l")); addKeys(root,listOf("⇧","z","x","c","v","b","n","m","⌫")); addKeys(root,listOf("?123",",","Space",".","↵"))
  return root
 }
 private fun addKeys(root:LinearLayout,keys:List<String>) { val r=row(); keys.forEach { k->r.addView(btn(k){ if(k=="⌫") currentInputConnection?.deleteSurroundingText(1,0) else if(k=="Space") currentInputConnection?.commitText(" ",1) else if(k=="↵") currentInputConnection?.commitText("\n",1) else currentInputConnection?.commitText(k,1) },LinearLayout.LayoutParams(0,d(43),1f).apply{setMargins(d(1),d(1),d(1),d(1))}) };root.addView(r) }
 private fun pasteAndTranslate() { val pasted=pasteBox.text.toString().ifBlank { val cm=getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager; if(cm.hasPrimaryClip()) cm.primaryClip?.getItemAt(0)?.coerceToText(this)?.toString().orEmpty() else "" }; if(pasted.isBlank()){status.text="Copy a message or paste it into the box first.";return}; preview.text=sample(pasted,target); status.text="Demo result only — live translation service not connected." }
 private fun sample(input:String,lang:String):String { val s=input.trim().lowercase(); if(lang=="English") return mapOf("oyata kohomada?" to "How are you?","oyata kohomada" to "How are you?","suba udasanak" to "Good morning","sthuthiyi" to "Thank you","mama hondin" to "I am fine").getOrDefault(s,"Live translation for $lang will be added with a translation service."); return "Live translation for $lang will be added with a translation service." }
}
