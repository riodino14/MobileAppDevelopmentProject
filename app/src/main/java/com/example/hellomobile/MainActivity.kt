package com.example.hellomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// Student class
data class Student(
    val name: String,
    val id: String,
    val gpa: String
)


// Main Activity
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                StudentList()
            }
        }
    }
}


// StudentList function
@Composable
fun StudentList() {

    // TextField states
    var name by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var gpa by remember { mutableStateOf("") }

    // Student list
    val students = remember {
        mutableStateListOf<Student>()
    }

    // Controls whether the output is displayed
    var showOutput by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Title
        Text(
            text = "Student Information",
            style = MaterialTheme.typography.headlineSmall
        )


        // Name TextField
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Name")
            },
            modifier = Modifier.fillMaxWidth()
        )


        // ID TextField
        OutlinedTextField(
            value = id,
            onValueChange = {
                id = it
            },
            label = {
                Text("ID")
            },
            modifier = Modifier.fillMaxWidth()
        )


        // GPA TextField
        OutlinedTextField(
            value = gpa,
            onValueChange = {
                gpa = it
            },
            label = {
                Text("GPA")
            },
            modifier = Modifier.fillMaxWidth()
        )


        // Input Button
        Button(
            onClick = {

                // Add a new Student object to the list
                students.add(
                    Student(
                        name = name,
                        id = id,
                        gpa = gpa
                    )
                )

                // Clear the input fields
                name = ""
                id = ""
                gpa = ""

                // Hide previous output
                showOutput = false
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Input")
        }


        // Output Button
        Button(
            onClick = {
                showOutput = true
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Output")
        }


        // Display students
        if (showOutput) {

            Text(
                text = "Student List",
                style = MaterialTheme.typography.titleLarge
            )


            students.forEachIndexed { index, student ->

                Text(
                    text = "${index + 1}. Name: ${student.name}\n" +
                            "   ID: ${student.id}\n" +
                            "   GPA: ${student.gpa}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}