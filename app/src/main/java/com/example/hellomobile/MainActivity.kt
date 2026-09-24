package com.example.hellomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Data model mahasiswa
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

// Composable function
@Composable
fun StudentList() {
    // State input form
    var name by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var gpa by remember { mutableStateOf("") }

    // List penampung data mahasiswa
    val students = remember {
        mutableStateListOf<Student>()
    }

    // State untuk mengontrol visibilitas output
    var showOutput by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding() // Mencegah tampilan menabrak status bar / jam atas
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // TextField Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // TextField ID
        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID") },
            modifier = Modifier.fillMaxWidth()
        )

        // TextField GPA
        OutlinedTextField(
            value = gpa,
            onValueChange = { gpa = it },
            label = { Text("GPA") },
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol Input & Output berdampingan
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Button Input
            Button(
                onClick = {
                    if (name.isNotBlank() && id.isNotBlank() && gpa.isNotBlank()) {
                        students.add(
                            Student(
                                name = name,
                                id = id,
                                gpa = gpa
                            )
                        )
                        // Kosongkan form setelah input
                        name = ""
                        id = ""
                        gpa = ""
                        showOutput = false
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Input")
            }

            // Button Output
            Button(
                onClick = {
                    showOutput = true
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Output")
            }
        }

        // Tampilan daftar mahasiswa setelah tombol Output diklik
        if (showOutput) {
            Text(
                text = "Student List:",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            students.forEachIndexed { index, student ->
                Text(
                    text = "${index + 1}. Name: ${student.name}, ID: ${student.id}, GPA: ${student.gpa}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}