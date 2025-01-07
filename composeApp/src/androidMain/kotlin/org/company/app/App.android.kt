package org.company.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import org.company.app.root.RootComponent
import org.company.app.root.RootUi

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RootUi(RootComponent(defaultComponentContext())) }
    }
}

@Preview
@Composable
fun AppPreview() { App() }
