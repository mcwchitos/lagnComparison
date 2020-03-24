package com.example.loginin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class MyThread implements Runnable {
    private boolean onRun;

    String name;
    int maxTimer;
    ProgressBar timer;
    Thread t;

    MyThread(String threadName, int maxTimer, ProgressBar pg)
    {
        name = threadName;
        this.maxTimer = maxTimer;
        timer = pg;
        t = new Thread(this, name);
        onRun = false;
        t.start();
    }
    // execution of thread starts from run() method
    public void run()
    {
        onRun = true;
        while (onRun) {
            for (int i = maxTimer; i >= 0; --i){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timer.setProgress(100 - (int) (((double)i / (double)maxTimer) * 100));
            }
            onRun = false;
        }
        System.out.println(name + " Stopped.");
    }

    // for stopping the thread
    public void stop()
    {
        onRun = false;
    }
}

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    int maxTimer = 5;
    ProgressBar timer;
    SeekBar slider;
    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    MyThread curT;
    Date d1, d2;
    Spinner drop;
    Button increment, book, reset;
    TextView Total;
    int integer = 0;
    EditText cel, fahr, date1, date2;
    boolean isCel, isFahr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isCel = false;
        isFahr = false;

        timer = (ProgressBar)findViewById(R.id.timer);
        slider = (SeekBar)findViewById(R.id.slider);
        reset = (Button)findViewById(R.id.reset);

        book = (Button)findViewById(R.id.book);
        drop = (Spinner)findViewById(R.id.dropDown);
        date1 = (EditText)findViewById(R.id.date1);
        date2 = (EditText)findViewById(R.id.date2);

        TextView txt = (TextView)findViewById(R.id.wellcome);
        increment = (Button)findViewById(R.id.increment);
        Total = (TextView)findViewById(R.id.display);
        txt.setText("Welcome To " + getIntent().getStringExtra("name"));

        cel  = (EditText)findViewById(R.id.celsiusNum);
        fahr = (EditText)findViewById(R.id.fahrNum);

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxTimer = progress;
                System.out.println(maxTimer);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curT != null)
                    curT.stop();
                MyThread th = new MyThread("Reset", maxTimer, timer);
                curT = th;
                th.run();
            }
        });


        drop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (drop.getSelectedItem().toString().equals("one-way flight")) {
                    date2.setEnabled(false);
                    d2 = null;
                }
                else
                    date2.setEnabled(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        date1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String line = date1.getText().toString();
                try {
                    d1 = df.parse(line);
                    if (d1 != null && d2 != null &&
                            d1.getYear() * 10000 + d1.getMonth() * 100 + d1.getDate() <
                                    d2.getYear() * 10000 + d2.getMonth() * 100 + d2.getDate())
                        book.setEnabled(true);
                    else if (d1 != null && d2 == null)
                        book.setEnabled(true);
                    else
                        book.setEnabled(false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        date2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String line = date2.getText().toString();
                try {
                    d2 = df.parse(line);
                    if (d1 != null && d2 != null &&
                            d1.getYear() * 10000 + d1.getMonth() * 100 + d1.getDate() <
                                    d2.getYear() * 10000 + d2.getMonth() * 100 + d2.getDate())
                        book.setEnabled(true);
                    else if (d1 != null && d2 == null)
                        book.setEnabled(false);
                    else
                        book.setEnabled(false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        increment.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                integer = integer + 1;
                Total.setText(Integer.toString(integer));
            }
        });

        cel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFahr) return;
                isCel = true;
                String line = cel.getText().toString();
                if (!line.equals("")){
                    System.out.println("cel" + line);
                    double celsius = Double.parseDouble(cel.getText().toString());
                    String newLine = Double.toString(9/5d * celsius + 32);
                    if (!newLine.equals(fahr.getText().toString()))
                        fahr.setText(newLine);
                }
                isCel = false;
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        fahr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isCel) return;
                isFahr = true;
                String line = fahr.getText().toString();
                if (!line.equals("")){
                    System.out.println("fahr" + line);
                    double fahrenheit = Double.parseDouble(fahr.getText().toString());
                    String newLine = Double.toString(5/9d * (fahrenheit - 32));
                    if (!newLine.equals(cel.getText().toString()))
                        cel.setText(newLine);
                }
                isFahr = false;
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}
