package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private Button version;
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

        //initialize the CalBrain utility class
        calculator = new CalBrain();

        // Capture display textview and version button from layout
        display = (TextView) findViewById(R.id.display);
        version = (Button) findViewById(R.id.btnVersion);

        // Setup number buttons
        setUpButtonListeners(R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9);

        // Setup operator buttons
        setUpOperatorButtons(R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide);

        // Clear and equals button
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());

        // Calculate
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculate());
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
                // Show the current input followed by the operator for feedback
                //display.setText(calculator.getCurrentInput() + " " + button.getText().toString());
                display.setText(calculator.getCurrentInput());
            });
        }
    }

    // Perform the calculation and update the display
    private void calculate() {
        String result = calculator.calculate();
        display.setText(result);
    }

    // Clear the input and reset the calculator state
    private void clear() {
        calculator.clear();
        display.setText("0");
    }

}