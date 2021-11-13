package com.jacob.composableworkflow

import androidx.compose.runtime.Composable

interface Workflow<Props, Output, Rendering> {
    @Composable
    fun render(props: Props, output: (Output) -> Unit): Rendering
}

interface ComposeRendering<T : Any> {
    @Composable
    fun Render(rendering: T)
}