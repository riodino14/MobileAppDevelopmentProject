
package com.example.hellomobile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.safeDrawingPadding


data class RegistrationSummary(
    val name: String,
    val studentId: String,
    val plan: String,
    val notifications: Boolean,
    val agreed: Boolean
)

fun validateRegistration(
    name: String,
    studentId: String,
    agreed: Boolean
): String? {
    return when {
        name.isBlank() -> "Please enter your name."
        studentId.isBlank() -> "Please enter your Student ID."
        !agreed -> "You must agree to the terms."
        else -> null
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                StudentRegistrationScreen()
            }
        }
    }
}

@Composable
fun StudentRegistrationScreen() {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var selectedPlan by remember { mutableStateOf("Standard") }
    var notifications by remember { mutableStateOf(true) }
    var agreed by remember { mutableStateOf(false) }

    var summary by remember {
        mutableStateOf<RegistrationSummary?>(null)
    }

    val plans = listOf("Basic", "Standard", "Expert")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Student Registration",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                summary = null
            },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = studentId,
            onValueChange = {
                studentId = it
                summary = null
            },
            label = { Text("Student ID") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Select Plan",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        plans.forEach { plan ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedPlan = plan
                        summary = null
                    }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedPlan == plan,
                    onClick = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = plan)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Notifications: " +
                        if (notifications) "ON" else "OFF",
                fontSize = 16.sp
            )

            Switch(
                checked = notifications,
                onCheckedChange = {
                    notifications = it
                    summary = null
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreed,
                onCheckedChange = {
                    agreed = it
                    summary = null
                }
            )

            Text(
                text = "I agree to the terms.",
                modifier = Modifier.clickable {
                    agreed = !agreed
                    summary = null
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    val error = validateRegistration(
                        name,
                        studentId,
                        agreed
                    )

                    if (error != null) {
                        summary = null

                        Toast.makeText(
                            context,
                            error,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        summary = RegistrationSummary(
                            name = name.trim(),
                            studentId = studentId.trim(),
                            plan = selectedPlan,
                            notifications = notifications,
                            agreed = agreed
                        )
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Preview")
            }


            Button(
                onClick = {
                    val error = validateRegistration(
                        name,
                        studentId,
                        agreed
                    )

                    if (error == null) {
                        Toast.makeText(
                            context,
                            "Submitted",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Please fix errors",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (agreed)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant,

                    contentColor = if (agreed)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Submit")
            }
        }

            Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        summary?.let { result ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Registration Summary",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider()

                    Text("Name: ${result.name}")
                    Text("ID: ${result.studentId}")
                    Text("Plan: ${result.plan}")

                    Text(
                        "Notifications: " +
                                if (result.notifications) "ON" else "OFF"
                    )

                    Text("Agreed: ${result.agreed}")
                }
            }
        }
    }
}
