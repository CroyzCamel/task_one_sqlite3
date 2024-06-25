package com.example.task_one_sqlite3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task_one_sqlite3.data.Task
import com.example.task_one_sqlite3.model.TaskDatabaseHelper
import com.example.task_one_sqlite3.ui.theme.Task_one_sqlite3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Task_one_sqlite3Theme {
                val dbHelper = TaskDatabaseHelper(this@MainActivity)
                TaskApp(dbHelper)
            }
        }
    }
}

@Composable
fun TaskApp(dbHelper: TaskDatabaseHelper) {
    var tasks by remember { mutableStateOf(dbHelper.getAllTasks()) }
    var newTaskTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = newTaskTitle,
            onValueChange = { newTaskTitle = it },
            label = { Text(text = "New Task") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            dbHelper.addTask(newTaskTitle)
            tasks = dbHelper.getAllTasks()
            newTaskTitle = ""
        }) {
            Text(text = "Add Task")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TaskList(tasks = tasks)
    }
}

@Composable
fun TaskList(tasks: List<Task>) {
    LazyColumn {
        items(tasks) { task ->
            Text(task.title, modifier = Modifier.padding(8.dp))
        }
    }
}



