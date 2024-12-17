package com.example.calculator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HistoryActivity extends AppCompatActivity {
    private TextView historyDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the TextView
        historyDisplay = findViewById(R.id.historyDisplay);

        // Get the history from the intent
        String history = getIntent().getStringExtra("history");

        // Display the history in the TextView
        if (history != null) {
            historyDisplay.setText(history);
        } else {
            historyDisplay.setText("No history available.");
        }

        // Set the back button to return to MainActivity
        findViewById(R.id.btnBack).setOnClickListener(v -> finish()); // Close this activity

    }
}