package com.saidtovar.asimplechat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemFirstLetterProfile(
    name: String,
    background: Brush = Brush.verticalGradient(
        listOf(
            Color.LightGray,
            Color.DarkGray
        )
    )
){

    Box (
        modifier = Modifier.size(50.dp)
            .clip(CircleShape)
            .background(background),
        contentAlignment = Alignment.Center
    ){

        Text(
            text = (name.firstOrNull() ?: ' ').toString(),
            fontSize = 20.sp
        )

    }

}