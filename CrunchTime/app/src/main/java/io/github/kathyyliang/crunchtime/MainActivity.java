package io.github.kathyyliang.crunchtime;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner exercises_spinner = (Spinner) findViewById(R.id.exercises_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.exercises, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exercises_spinner.setAdapter(adapter);
        exercises_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = (String) parent.getItemAtPosition(pos);
                TextView reps_or_min = (TextView) findViewById(R.id.reps_or_min);
                Resources res = getResources();
                String[] reps = res.getStringArray(R.array.reps);
                if (Arrays.asList(reps).contains(item)) {
                    reps_or_min.setText("reps");
                } else {
                    reps_or_min.setText("min");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        Button convert = (Button) findViewById(R.id.button);
        convert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText number = (EditText) findViewById(R.id.number);
                if (number.getText().toString().trim().length() != 0) {
                    TextView calorie_count = (TextView) findViewById(R.id.calorie_count);
                    calorie_count.setText(convertToCalories());
                    TextView equivalent = (TextView) findViewById(R.id.equivalent);
                    equivalent.setText(convertFromCalories(Double.parseDouble((String) calorie_count.getText())));
                }
            }
        });

        Spinner equivalent_spinner = (Spinner) findViewById(R.id.equivalent_exercises_spinner);
        ArrayAdapter<CharSequence> equivalent_adapter = ArrayAdapter.createFromResource(this, R.array.exercises, android.R.layout.simple_spinner_item);
        equivalent_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equivalent_spinner.setAdapter(equivalent_adapter);
        equivalent_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = (String) parent.getItemAtPosition(pos);
                TextView equivalent_reps_or_min = (TextView) findViewById(R.id.equivalent_reps_or_min);
                Resources res = getResources();
                String[] reps = res.getStringArray(R.array.reps);
                if (Arrays.asList(reps).contains(item)) {
                    equivalent_reps_or_min.setText("reps");
                } else {
                    equivalent_reps_or_min.setText("min");
                }
                TextView equivalent = (TextView) findViewById(R.id.equivalent);
                TextView calorie_count = (TextView) findViewById(R.id.calorie_count);
                equivalent.setText(convertFromCalories(Double.parseDouble((String) calorie_count.getText())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    public String convertToCalories() {
        Resources res = getResources();
        String[] exercises = res.getStringArray(R.array.exercises);
        int[] numbers = res.getIntArray(R.array.numbers);
        EditText editText = (EditText) findViewById(R.id.number);
        double number = Double.parseDouble(editText.getText().toString());
        Spinner exercises_spinner = (Spinner) findViewById(R.id.exercises_spinner);
        String exercise = (String) exercises_spinner.getSelectedItem();
        double ans = 0.0;
        for (int i = 0; i < exercises.length; i++) {
            if (exercise.equals(exercises[i])) {
                ans = Math.round(number * 10000 / numbers[i]) / 100.0;
            }
        }
        return Double.toString(ans);
    }

    public String convertFromCalories(double calories) {
        Resources res = getResources();
        String[] exercises = res.getStringArray(R.array.exercises);
        int[] numbers = res.getIntArray(R.array.numbers);
        Spinner equivalent_spinner = (Spinner) findViewById(R.id.equivalent_exercises_spinner);
        String equivalent_exercise = (String) equivalent_spinner.getSelectedItem();
        double ans = 0.0;
        for (int j = 0; j < exercises.length; j++) {
            if (equivalent_exercise.equals(exercises[j])) {
                ans = Math.round(calories * numbers[j]) / 100.0;
            }
        }
        return Double.toString(ans);
    }
}
