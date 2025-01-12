package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private TextView historyView;
    private SwitchMaterial switchHistory;
    private CalBrain calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the CalBrain utility class
        calculator = new CalBrain();

        // Capture display textview and version button from layout
        display = (TextView) findViewById(R.id.display);
        historyView = (TextView) findViewById(R.id.history);
        switchHistory = findViewById(R.id.switchHistory);

        // Setup number buttons
        setUpButtonListeners(R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9);

        // Setup operator buttons
        setUpOperatorButtons(R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide);

        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());

        // Equal button
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculate());

        // Toggle history visibility when the button is clicked
        switchHistory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show the history if toggle is checked
                updateHistory();
                historyView.setVisibility(View.VISIBLE);
            } else {
                // Hide the history if toggle is unchecked
                historyView.setVisibility(View.GONE);
            }
        });

        // Button to go to the History Activity
        findViewById(R.id.btnVersion).setOnClickListener(v -> {
            // Pass history to HistoryActivity
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            String history = getHistoryAsString();
            intent.putExtra("history", history);
            startActivity(intent);
        });

        if (savedInstanceState != null) {
            // Restore the state from the savedInstanceState
            String savedData = savedInstanceState.getString("inputData");
            String savedHistory = savedInstanceState.getString("historyData");
            if(savedData != null) {
                display.setText(savedData);
            }
            if (savedHistory != null){
                historyView.setText(savedHistory);
            }
        }

    }//OnCreate ends

    // Set up same listener for all number buttons
    private void setUpButtonListeners(int... buttonIds) {
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                calculator.appendNumber(button.getText().toString());
                display.setText(calculator.getCurrentInput()); // Show button pressed
            });
        }
    }

    // Set up listeners for all operator buttons
    private void setUpOperatorButtons(int... buttonIds) {
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                calculator.setOperator(button.getText().toString());
                display.setText(calculator.getCurrentInput());
            });
        }
    }

    // Perform the calculation and update the display
    private void calculate() {
        String result = calculator.calculate();
        display.setText(result);
        // If the history is visible, update the history
        if (switchHistory.isChecked()) {
            updateHistory();
        }
    }

    // Clear the input and reset the calculator state
    private void clear() {
        calculator.clear();
        display.setText("0");
        historyView.setText("");
    }

    // Update the history TextView with the calculation history
    private void updateHistory(){
        List<String> history = calculator.getHistory(); // Get history from CalBrain
        StringBuilder historyText = new StringBuilder("History:\n");

        // Append all history entries to the StringBuilder
        for (String entry : history) {
            historyText.append(entry).append("\n");
        }

        // Update the TextView to show the full history
        historyView.setText(historyText.toString());
    }

    // Get the history as a single string to pass to the next Activity
    private String getHistoryAsString() {
        List<String> history = calculator.getHistory();
        StringBuilder historyText = new StringBuilder();
        for (String entry : history) {
            historyText.append(entry).append("\n");
        }
        return historyText.toString();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current input data
        if(display != null){
            outState.putString("inputData", display.getText().toString());
        }
        if(historyView != null){
            outState.putString("historyData", historyView.getText().toString());
        }
    }

}