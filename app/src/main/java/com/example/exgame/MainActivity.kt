package com.example.exgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeGame()
        }
    }
}

@Composable
fun TicTacToeGame() {
    // ✅ Use List of MutableState to track each cell separately
    var board = remember {
        List(3) { row -> List(3) { col -> mutableStateOf("") } }
    }
    var currentPlayer = remember { mutableStateOf("x") }
    var winner = remember { mutableStateOf<String?>(null) }

    fun checkWinner(): String? {
        val lines = listOf(
            // Rows
            listOf(board[0][0].value, board[0][1].value, board[0][2].value),
            listOf(board[1][0].value, board[1][1].value, board[1][2].value),
            listOf(board[2][0].value, board[2][1].value, board[2][2].value),
            // Columns
            listOf(board[0][0].value, board[1][0].value, board[2][0].value),
            listOf(board[0][1].value, board[1][1].value, board[2][1].value),
            listOf(board[0][2].value, board[1][2].value, board[2][2].value),
            // Diagonals
            listOf(board[0][0].value, board[1][1].value, board[2][2].value),
            listOf(board[0][2].value, board[1][1].value, board[2][0].value),
        )
        for (line in lines) {
            if (line.all { it == "x" }) return "x"
            if (line.all { it == "o" }) return "o"
        }
        return null
    }

    fun onCellClick(row: Int, col: Int) {
        if (board[row][col].value.isEmpty() && winner.value == null) {
            board[row][col].value = currentPlayer.value
            winner.value = checkWinner()
            if (winner.value == null) {
                currentPlayer.value = if (currentPlayer.value == "x") "o" else "x"
            }
        }
    }

    fun resetGame() {
        board.forEach { row -> row.forEach { it.value = "" } }
        currentPlayer.value = "x"
        winner.value = null
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = winner.value?.let { "Winner: $it 🎉" } ?: "Current Player: ${currentPlayer.value}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    Card(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .clickable { onCellClick(row, col) },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color.Black)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = board[row][col].value,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (board[row][col].value == "x") Color.Black else Color.Red
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { resetGame() }) {
            Text(text = "Restart Game")
        }
    }
}
