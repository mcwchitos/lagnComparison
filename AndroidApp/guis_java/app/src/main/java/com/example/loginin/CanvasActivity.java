package com.example.loginin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.loginin.R;

import java.util.ArrayList;

public class CanvasActivity extends Activity {

    class Circle{
        float x;
        float y;
        float r;

        public Circle(float x, float y, float r){
            this.x = x;
            this.y = y;
            this.r = r;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    class DrawView extends SurfaceView {

        ArrayList<Circle> db = new ArrayList<>();
        SurfaceHolder surfaceHolder;
        Paint p;
        private float r = 60;

        public DrawView(Context context) {
            super(context);
            surfaceHolder = getHolder();
            p = new Paint();
            p.setColor(Color.BLACK);
            p.setStyle(Paint.Style.FILL);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            System.out.println("x: " + x + ", y: " + y);
            Circle cir = new Circle(x, y, r);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("down");
                    if (x < 150 && y > 2500){
                        if (db.size() != 0)
                            db.remove(db.size() - 1);
                        return true;
                    }
                    db.add(cir);
                    break;
                case MotionEvent.ACTION_MOVE:
                    System.out.println("move");
                    break;
                case MotionEvent.ACTION_UP:
                    if (surfaceHolder.getSurface().isValid()) {
                        Canvas canvas = surfaceHolder.lockCanvas();
                        canvas.drawColor(Color.WHITE);
                        canvas.drawRect(0, 2500, 150, 2700, p);
                        for (Circle crl:db) {
                            canvas.drawCircle(crl.x, crl.y, crl.r, p);
                        }
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    System.out.println("cancel");
                    break;
            }

            return true;
        }

    }
}
