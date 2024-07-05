package it.hoseinahmadi.filepermission

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.hoseinahmadi.filepermission.ui.theme.FilePermissionTheme
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilePermissionTheme {
                Init()
            }
        }
    }
}


@Composable
fun Init() {
    val context = LocalContext.current
    var fileName by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = fileName,
                onValueChange = { fileName = it },
                label = { Text(text = "File Name") },
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text(text = "Content") },
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    try {
                        val fileOutPutString = context.openFileOutput(fileName, MODE_PRIVATE)
                        fileOutPutString.write(content.toByteArray())
                    }catch (e:Exception){
                        Log.e("hosein","error : ${e.message}")
                    }

                }) {
                    Text(text = "Save")
                }

                Button(onClick = {
                    val fileInputString =context.openFileInput(fileName)
                     val inputStringReader =InputStreamReader(fileInputString)
                    val bufferedReader =BufferedReader(inputStringReader)
                    val txt =bufferedReader.readText()
                    content =txt
                }) {
                    Text(text = "load")
                }

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Init()
}

