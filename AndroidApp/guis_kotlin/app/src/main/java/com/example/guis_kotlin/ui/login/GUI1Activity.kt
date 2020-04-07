package com.example.guis_kotlin.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.guis_kotlin.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_g_u_i1.*
import kotlinx.android.synthetic.main.content_g_u_i1.*

class GUI1Activity : AppCompatActivity() {

    var inFahr = false
    var inCel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_u_i1)
        setSupportActionBar(toolbar)

        fab1.setOnClickListener { view ->
            Snackbar.make(view, "Next GUI", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        increase.setOnClickListener{ _ ->
            count.text = (count.text.toString().toInt() + 1).toString()
            println(count.text)
        }
        decrease.setOnClickListener{ _ ->
            count.text = (count.text.toString().toInt() - 1).toString()
            println(count.text)
        }
        celsiusNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (inFahr) return;
                inCel = true;
                var line = celsiusNum.text;
                if (!line.equals("")){
                    var celsius = celsiusNum.text.toString().toDouble();
                    var newLine = (9/5f * celsius + 32).toString();
                    if (!newLine.equals(fahrNum.text.toString()))
                        fahrNum.setText(newLine);
                }
                inCel = false;
            }
        })

        fahrNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (inCel) return;
                inFahr = true;
                var line = fahrNum.text;
                if (!line.equals("")){
                    var fahr = fahrNum.text.toString().toDouble();
                    var newLine = (9/5f * fahr + 32).toString();
                    if (!newLine.equals(celsiusNum.text.toString()))
                        celsiusNum.setText(newLine);
                }
                inFahr = false;
            }
        })
    }
}
