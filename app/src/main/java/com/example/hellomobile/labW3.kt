// create student class 
data class Student{
    val name: String ; //we use val -> val means that the variable is immutable while var is mutable. So, immutable means the value can not be changed. 
    val id: String; 
    val gpa: String ; // we use string for this variable because we follow the instruction from lecture. usually this kind of variable use float or double. 
}

// we need 3 state for textfield 
// now we have 3 input for each field 
var name by remember { mutableStateOf("")} // storing name value textfield
var id by remember { mutableStateOf("")} 
var gpa by remember { mutableStateOf("")}

// now we have to make the variable for storing a student (we use list)
val students = remember { mutableStateListOf<student>()} // this list must be from student object

Button(
    onClick = {
        students.add(
            student(
                name = name, 
                id = id, 
                gpa = gpa 
            )
        )
    }
) {
    Text("Input")
}

onClick = {
    students.add(
        student(
            name = name, 
            id = id, 
            gpa = gpa 
        )
    )
    name = ""
    id = ""
    gpa = ""
}


// creating 3 textfield 
OutlinedTextField(
    value = name, 
    onValueChange = {name = it}, 
    label = {Text("name")}
)

OutlinedTextField(
    value = id, 
    onValueChange = {id = it},
    label = {Text("ID")}
)

OutlinedTextField(
    value = gpa, 
    onValueChange = {gpa = it},
    label = {Text("GPA")}
)


// we build the output button 
Button (
    onClick = {
        showOutput = true
    }
) {
    Text("Output")
}

students.forEachIndexed{ index, student -> 
    Text(
        text = "${index + 1}. Name: ${student.name}\n" +
        "   ID: ${student.id}\n" +
        "   GPA:${student.gpa}"
    )
}


