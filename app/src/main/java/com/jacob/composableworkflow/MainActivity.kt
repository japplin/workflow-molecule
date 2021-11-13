package com.jacob.composableworkflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.molecule.AndroidUiDispatcher
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val moleculeRendering: StateFlow<Any> = CoroutineScope(AndroidUiDispatcher.Main)
            .launchMolecule {
                EmployeeListWorkflow(
                    EmployeeListService(),
                    EmployeeDetailWorkflow(Dialer(this))
                ).render(Unit) {}
            }

        setContent {
            val renderer: Any by moleculeRendering.collectAsState()
            (renderer as ComposeRendering<Any>).Render(renderer)
        }
    }
}