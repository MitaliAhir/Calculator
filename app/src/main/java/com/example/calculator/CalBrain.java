package com.example.calculator;

public class CalBrain {

    String currentInput;
    private int firstOperand;
    private int secondOperand; // Stores the second operand (current input)
    private String operator;

    public CalBrain() {
        this.firstOperand = 0;
        this.secondOperand = 0;
        this.operator = "";
        this.currentInput = "";
    }

    // Handle number input
    public void appendNumber(String number) {
        currentInput += number;
    }

    // Handle operator input
    public void setOperator(String operator) {
        if (!currentInput.isEmpty()) {
            if (this.operator.isEmpty()) {
                // First time setting the operator, store the first operand
                firstOperand = Integer.parseInt(currentInput);
            } else {
                // If operator already exists, perform the calculation
                calculate(); // Calculate the previous result
            }
            this.operator = operator;
            // Append the operator to the current input for display purposes
            currentInput = String.valueOf(firstOperand) + " " + operator;
        }
    }

//    pares the string list and calculate the result.
//    Handle calculation based on operator
    public String calculate() {
        if (operator.isEmpty()) {
            return "Error: No operator"; // Error if no operator is set
        }
        if (!currentInput.isEmpty()) {
            // Split the current input into operator and second operand
            String[] parts = currentInput.split(" "); // Split by space
            if (parts.length != 2) {
                return "Error: Invalid input"; // Handle invalid format
            }

            double secondOperand = Integer.parseInt(parts[1].trim());  // Parse the second operand (after operator)
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result =  (double) firstOperand / secondOperand;
                    } else {
                        return "Undefined"; // Return undefined for division by 0
                    }
                    break;
                default:
                    return "Error"; // Return error if operator is invalid
            }

            // Show the result as an integer if it's a whole number
            if (result == (int) result) {
                currentInput = String.valueOf((int) result); // Convert to integer
            } else {
                currentInput = String.valueOf(result); // Display as decimal if not whole
            }
            operator = ""; // Reset operator after calculation
            return currentInput;
        }
    return currentInput; // If input is empty, return empty string
}

    // Reset the calculator
    public void clear() {
        currentInput = "";
        operator = "";
        firstOperand = 0;
    }
    // Get the current input (to show on the display)
    public String getCurrentInput() {
        return currentInput.isEmpty() ? "0" : currentInput;
    }
}
