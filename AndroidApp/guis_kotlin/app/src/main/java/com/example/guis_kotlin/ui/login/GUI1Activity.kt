package com.example.guis_kotlin.ui.login

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.guis_kotlin.R

import kotlinx.android.synthetic.main.activity_g_u_i1.*
import kotlinx.android.synthetic.main.content_g_u_i1.*

class GUI1Activity : AppCompatActivity() {

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
    }
}
