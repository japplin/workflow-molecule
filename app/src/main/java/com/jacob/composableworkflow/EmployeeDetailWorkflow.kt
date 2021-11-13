package com.jacob.composableworkflow

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString


class EmployeeDetailWorkflow(
    private val dialer: Dialer
) : Workflow<EmployeeDetailsProps, EmployeeDetailsBackPressed, EmployeeDetailRendering> {
    @Composable
    override fun render(
        props: EmployeeDetailsProps,
        output: (EmployeeDetailsBackPressed) -> Unit
    ): EmployeeDetailRendering {
        val employee = props.employee
        return EmployeeDetailRendering(
            employee.name,
            employee.number,
            onNumberClicked = { dialer.call(employee.number) },
            onBackPressed = { output(EmployeeDetailsBackPressed) }
        )
    }
}

object EmployeeDetailsBackPressed

data class EmployeeDetailsProps(val employee: Employee)

data class EmployeeDetailRendering(
    val name: String,
    val number: Int,
    val onNumberClicked: () -> Unit,
    val onBackPressed: () -> Unit
) : ComposeRendering<EmployeeDetailRendering> {
    @Composable
    override fun Render(rendering: EmployeeDetailRendering) {
        Column {
            TopAppBar(title = { Text(name) }, navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back Arrow")
                }
            })
            Text(rendering.name)
            ClickableText(
                AnnotatedString("${rendering.number}"),
                onClick = { rendering.onNumberClicked() }
            )
        }
    }
}

class Dialer(private val activity: Activity) {
    fun call(number: Int) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel: $number")
        activity.startActivity(intent)
    }
}