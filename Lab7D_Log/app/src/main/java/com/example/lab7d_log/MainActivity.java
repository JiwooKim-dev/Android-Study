package com.example.lab7d_log;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tv_display);
        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        startAsyncCalc();
    }

    private void startAsyncCalc() {

        AsyncCalcTask task = new AsyncCalcTask();
        task.execute(1, Integer.MAX_VALUE);
    }

    class AsyncCalcTask extends AsyncTask<Integer, Integer, Integer>{

        /* Async Thread Method */
        @Override
        protected Integer doInBackground(Integer... integers) {

            int number = integers[0];
            int count = integers[1];
            int result = 0;

            int percentUnit = count / 100;

            for (int i=0 ; i<count ; i++){
                result += number;

                if (result % percentUnit == 0){
                    publishProgress(result/percentUnit);
                }
            }
            return result;
        }

        /* UI Thread Method (실행중) */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvDisplay.setText(values[0] + "%");
            Log.d("AsyncTask", values[0] + "%");
        }

        /* UI Thread Method (실행결과) */
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            tvDisplay.setText("Result : " + integer);
        }
    }
}
