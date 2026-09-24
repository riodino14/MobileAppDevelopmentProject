package com.example.hellomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BMICalculator()
        }
    }
}

@Composable
fun BMICalculator() {

    // State untuk menyimpan berat badan
    var weight by remember { mutableStateOf("") }

    // State untuk menyimpan tinggi badan
    var height by remember { mutableStateOf("") }

    // State untuk menyimpan hasil
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 40.dp,
                bottom = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Title
        Text(
            text = "BMI Calculator"
        )

        // Weight input
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = {
                Text("Weight (kg)")
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Height input
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = {
                Text("Height (cm)")
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Calculate button
        Button(
            onClick = {

                // Convert String to Double
                val weightValue = weight.toDoubleOrNull()
                val heightValue = height.toDoubleOrNull()

                // Validate input
                if (
                    weightValue != null &&
                    heightValue != null &&
                    heightValue > 0
                ) {

                    // Convert cm to meter
                    val heightMeter = heightValue / 100

                    // Calculate BMI
                    val bmi = weightValue /
                            (heightMeter * heightMeter)

                    // Display result
                    result = "BMI: ${
                        String.format(
                            Locale.US,
                            "%.2f",
                            bmi
                        )
                    }"

                } else {

                    // Display error
                    result = "Please enter valid numbers!"
                }
            }
        ) {
            Text("Calculate BMI")
        }

        // Display result
        Text(
            text = result,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}