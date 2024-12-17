package com.example.calculator;

import java.util.ArrayList;
import java.util.List;

public class CalBrain {

    String currentInput; // Holds the input the user is entering
    private int currentValue; // Stores the result of the calculation, or the first operand before the operator is applied
    private String operator;
    private boolean isOperatorSet;
    private List<String> history;

    public CalBrain() {
        this.currentValue= 0;
        this.operator = "";
        this.currentInput = "";
        this.isOperatorSet = false;
        this.history = new ArrayList<>();
    }

    // Handle number input
    public void appendNumber(String number) {
        currentInput += number;
    }

    // Handle operator input
    public void setOperator(String operator) {
        if (!currentInput.isEmpty()) {
            if (!isOperatorSet) {
                currentValue = Integer.parseInt(currentInput); // Save first operand
                currentInput = ""; // Clear the input for next operand
                isOperatorSet = true;
            } else {
                calculate(); // // Perform the calculation before setting a new operator
                currentValue = Integer.parseInt(currentInput); // Update first operand
                currentInput = "";  // Reset current input for the next operand
            }
            this.operator = operator; // Set the new operator
        }
    }

    // Handle calculation based on operator
    public String calculate() {
        if (!currentInput.isEmpty()) {
            int secondOperand = Integer.parseInt(currentInput);
            double result = 0;
            String calculation = "";

            switch (operator) {
                case "+":
                    result = currentValue + secondOperand;
                    break;
                case "-":
                    result = currentValue - secondOperand;
                    break;
                case "*":
                    result = currentValue * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result =  (double) currentValue / secondOperand;
                    } else {
                        return "Undefined"; // Return undefined for division by 0
                    }
                    break;
                default:
                    return "Error"; // Return error if operator is invalid
            }

            // Show the result as an integer if it's a whole number
            if (result == (int) result) {
                currentInput = String.valueOf((int) result);
                calculation = currentValue + " " + operator + " " + secondOperand + " = " + currentInput;
            } else {
                currentInput = String.valueOf(result); // Display as decimal if not whole
                calculation = currentValue + " " + operator + " " + secondOperand + " = " + currentInput;
            }
            // Add the current calculation to history
            history.add(calculation);

            operator = ""; // Reset operator after calculation
            isOperatorSet = false;
            return currentInput;
        }
    return currentInput; // If input is empty, return empty string
}

    // Reset the calculator
    public void clear() {
        currentInput = "";
        operator = "";
        currentValue = 0;
        isOperatorSet = false;
    }
    // Get the current input (to show on the display)
    public String getCurrentInput() {
        return currentInput.isEmpty() ? "0" : currentInput;
    }
    // Get the calculation history
    public List<String> getHistory() {
        return history;
    }

}
