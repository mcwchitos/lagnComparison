package com.example.guis_kotlin.ui.login

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.guis_kotlin.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_g_u_i1.*
import kotlinx.android.synthetic.main.content_g_u_i1.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    public var inFahr = false
    public var inCel = false
    public var maxTimer: Int = 5
    public var countDown: Int = 1

    companion object{
        class MyTask internal constructor(context: MainActivity) : AsyncTask<Int, Int, String>() {

            private val activityReference: WeakReference<MainActivity> = WeakReference(context)

            override fun doInBackground(vararg params: Int?): String {
                val activity = activityReference.get()
                for (countDown in activity!!.countDown..params[0]!!){
                    try {
                        Thread.sleep(1000)
                        activity.timer.progress = (100 - (countDown.toDouble() / activity.maxTimer.toDouble() * 100).toInt())
                    } catch (e: InterruptedException){
                        e.printStackTrace()
                    }
                }
                return "Task completed."
            }

            override fun onPostExecute(result: String?) {
                val activity = activityReference.get()
                activity!!.timer.visibility = View.GONE
                println(result)
            }

            override fun onProgressUpdate(vararg values: Int?) {
                val activity = activityReference.get()
                activity!!.timer.progress = values[0]!!
            }
        }
    }


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
                val line = celsiusNum.text;
                if (!line.equals("")){
                    val celsius = celsiusNum.text.toString().toDouble();
                    val newLine = (9/5f * celsius + 32).toString();
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
                val line = fahrNum.text;
                if (!line.equals("")){
                    val fahr = fahrNum.text.toString().toDouble();
                    val newLine = (9/5f * fahr + 32).toString();
                    if (!newLine.equals(celsiusNum.text.toString()))
                        celsiusNum.setText(newLine);
                }
                inFahr = false;
            }
        })
        nextBtn.setOnClickListener{ _ ->
            startActivity(Intent(this@MainActivity,CanvasActivity::class.java))
        }

        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                maxTimer = i
                println(maxTimer)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
        })
        reset.setOnClickListener { view ->
            countDown = 1
            timer.visibility = View.VISIBLE
            timer.progress = 0
            if (view.id == R.id.reset)
                MyTask(this).execute(maxTimer)
        }
    }
}
