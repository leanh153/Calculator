package com.facehouse.leanh.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Caculator extends AppCompatActivity {

    // Text edit to display text input
    private EditText editText;
    // TextView used to editText the output
    private TextView textResult;
    // IDs of all buttons
    private int[] Buttons = {
            R.id.btnZero, R.id.btnOne,
            R.id.btnTwo, R.id.btnThree,
            R.id.btnFour, R.id.btnFive,
            R.id.btnSix, R.id.btnSeven,
            R.id.btnEight, R.id.btnNine,
            R.id.btnAdd, R.id.btnSubtract,
            R.id.btnMultiply, R.id.btnDivide,
            R.id.btnDot, R.id.btnDelete,
            R.id.btnSquareRoot, R.id.btnClear,
            R.id.btnEqual, R.id.btn100,
            R.id.btnSin, R.id.btnCos,
            R.id.btnTan, R.id.btnCot,
            R.id.btnparenthese, R.id.btnparenthese2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the EditText
        this.editText = findViewById(R.id.textEdit);

        // Find the TextView
        this.textResult = findViewById(R.id.textResult);

        // set up onclick listener
        setButtonOnClick();
    }


    // set up event onclick and processing
    private void setButtonOnClick() {
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnClear:
                        editText.setText("");
                        textResult.setText("");
                        break;
                    case R.id.btnSquareRoot:
                        editText.append("√");
                        break;
                    case R.id.btnSin:
                        editText.append("sin(");
                        break;
                    case R.id.btnCos:
                        editText.append("cos(");
                        break;
                    case R.id.btnTan:
                        editText.append("tan(");
                        break;
                    case R.id.btnCot:
                        editText.append("cot(");
                        break;
                    case R.id.btnEqual:
                        calculate();
                        break;
                    case R.id.btnDelete:
                        // delete text in EditText, checking if edit text empty to prevent inDexOutOfBound exception
                        if (editText.length() > 0) {
                            editText.getText().delete(editText.length() - 1, editText.length());
                        }

                        textResult.setText("");
                        break;
                    default:
                        // get text from button and displaying text to screen
                        editText.append(((Button) v).getText().toString());
                }
            }
        };

        // Assign the listener to all buttons
        for (int id : Buttons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    // Calculate
    private void calculate() {
        String input = editText.getText().toString();
        try {
            if (input.length() > 0) {
                // seting character to specific character that exp4j library accept
                if (input.contains("x") | input.contains("√")) {
                    input = input.replaceAll("x", "*");
                    input = input.replaceAll("√", "sqrt");
                }

                //  build expression
                Expression expression = new ExpressionBuilder(input).build();
                // evaluate expression
                double result = expression.evaluate();
                // Displaying result to screen
                textResult.setText(String.valueOf(result));
            }

        } catch (Exception e) {
            try {
                if (e.getMessage().contains("Division by zero!")) {
                    // displaying this text if division by zero. need this try catch block to prevent NullPointerException
                    textResult.setText("Error! Division by zero.");
                } else {
                    // displaying this text if exception occur
                    textResult.setText(e.getMessage());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
