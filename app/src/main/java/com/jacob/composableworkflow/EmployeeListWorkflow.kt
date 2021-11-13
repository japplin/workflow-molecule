package com.jacob.composableworkflow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString

class EmployeeListWorkflow constructor(
    private val employeeListService: EmployeeListService,
    private val employeeDetailWorkflow: EmployeeDetailWorkflow
) : Workflow<Unit, Unit, Any> {

    @Composable
    override fun render(props: Unit, output: (Unit) -> Unit): Any {
        val employees: List<Employee>? by employeeListService.employees().collectAsState(null)
        var selectedEmployee: Employee? by remember { mutableStateOf(null) }

        selectedEmployee?.run {
            return employeeDetailWorkflow.render(EmployeeDetailsProps(employee = this)) {
                selectedEmployee = null
            }
        }
        return employees?.let { loadedEmployees ->
            LoadedEmployeeList(
                loadedEmployees,
                onEmployeeClicked = { selectedEmployee = it },
            )
        } ?: EmployeeListLoading
    }
}

sealed interface EmployeeListRendering

object EmployeeListLoading : EmployeeListRendering, ComposeRendering<EmployeeListLoading> {
    @Composable
    override fun Render(rendering: EmployeeListLoading) {
        CircularProgressIndicator()
    }
}

data class LoadedEmployeeList(
    val employees: List<Employee>,
    val onEmployeeClicked: (Employee) -> Unit
) : EmployeeListRendering, ComposeRendering<LoadedEmployeeList> {
    @Composable
    override fun Render(rendering: LoadedEmployeeList) {
        Scaffold(topBar = { TopAppBar(title = { Text("Employees List") }) }) {
            Column {
                employees.map { employee ->
                    Card {
                        ClickableText(
                            AnnotatedString(employee.name),
                            onClick = { onEmployeeClicked(employee) }
                        )
                    }
                }
            }
        }
    }
}
