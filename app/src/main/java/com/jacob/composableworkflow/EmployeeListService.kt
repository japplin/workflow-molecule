package com.jacob.composableworkflow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EmployeeListService {
    fun employees(): Flow<List<Employee>> = flow {
        kotlinx.coroutines.delay(1000)
        emit(List(5) { Employee("name$it", it + 1) })
    }
}

data class Employee(val name: String, val number: Int)