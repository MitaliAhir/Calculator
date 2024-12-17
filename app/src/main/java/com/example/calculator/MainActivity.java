package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private TextView historyView;
    private Button version;
    private ToggleButton toggleHistory;
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
        version = (Button) findViewById(R.id.btnVersion);
        toggleHistory = findViewById(R.id.toggleHistory);

        // Setup number buttons
        setUpButtonListeners(R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9);

        // Setup operator buttons
        setUpOperatorButtons(R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide);

        // Clear and equals button
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());

        // Calculate
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculate());

        // Toggle history visibility when the button is clicked
        toggleHistory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show the history if toggle is checked
                updateHistory();
                historyView.setVisibility(View.VISIBLE);
            } else {
                // Hide the history if toggle is unchecked
                historyView.setVisibility(View.GONE);
            }
        });

    }//OnCreate ends

    // Set up same listener for all number buttons
    private void setUpButtonListeners(int... buttonIds) {
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                calculator.appendNumber(button.getText().toString());
                display.setText(calculator.getCurrentInput()); // Show button pressed
                //display.append(button.getText().toString() + " ");
            });
        }
    }

    // Set up listeners for all operator buttons
    private void setUpOperatorButtons(int... buttonIds) {
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                calculator.setOperator(button.getText().toString());
                //display.setText(calculator.getCurrentInput() + " " + button.getText().toString());
                display.setText(calculator.getCurrentInput());
                //display.append(button.getText().toString() + " ");
            });
        }
    }

    // Perform the calculation and update the display
    private void calculate() {
        String result = calculator.calculate();
        display.setText(result);
        // If the history is visible, update the history
        if (toggleHistory.isChecked()) {
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

}