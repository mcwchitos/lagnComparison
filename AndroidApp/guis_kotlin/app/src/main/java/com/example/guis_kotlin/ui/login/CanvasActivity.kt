package com.example.guis_kotlin.ui.login

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import com.example.guis_kotlin.R

class CanvasActivity : AppCompatActivity() {

    class Circle(val x: Float, val y: Float, val r: Float) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }


    class DrawView(context: Context) : SurfaceView(context) {
        var db = ArrayList<Circle>()
        var surfaceHolder: SurfaceHolder = holder
        var p: Paint = Paint()
        private var r: Float = 60F

        init {
            p.color = Color.BLACK
            p.style = Paint.Style.FILL
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val x = event.x
            val y = event.y
            println("x: $x, y: $y")
            val cir = Circle(x, y, 60F)
            when (event.action){
                MotionEvent.ACTION_DOWN -> {
                    println("down")
                    if (x < 50 && y < 50){
                        if (db.size != 0)
                            db.removeAt(db.size - 1)
                        return true
                    }
                    db.add(cir)
                }
                MotionEvent.ACTION_UP ->{
                    println("up")
                    if (surfaceHolder.surface.isValid){
                        val canvas: Canvas = surfaceHolder.lockCanvas()
                        canvas.drawColor(Color.WHITE)
                        canvas.drawRect(0F, 0F, 50F, 50F, p)
                        db.forEach{ crl ->
                            canvas.drawCircle(crl.x, crl.y, crl.r, p)
                        }
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
                MotionEvent.ACTION_MOVE ->{
                    println("move")
                }
                MotionEvent.ACTION_CANCEL ->{
                    println("cancel")
                }
            }
            return true
        }

    }
}
