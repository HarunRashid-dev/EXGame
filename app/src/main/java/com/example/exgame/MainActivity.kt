package com.example.exgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.exgame.ui.theme.EXGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeGame()
                }
            }
        }

@Composable
fun TicTacToeGame(){
    var board by remember { mutableStateOf(Array(3) {Array(3){""} }) }
    var currentPlayer by remember{ mutableStateOf("x") }
    var winner by remember { mutableStateOf<String?>(null) }

    fun checkWinner(): String? {
        val lines = listOf(
            // Row
            listOf(board[0][0], board[0][1], board[0][2]),
            listOf(board[1][0], board[1][1], board[1][2]),
            listOf(board[2][0], board[2][1], board[2][2]),
            // Columns
            listOf(board[0][0], board[1][0], board[2][0]),
            listOf(board[0][1], board[1][1], board[2][1]),
            listOf(board[0][2], board[1][2], board[2][2]),
            //Diagonals
            listOf(board[0][0], board[1][1], board[2][2]),
            listOf(board[0][2], board[1][1], board[2][0]),
        )
        for (line in lines) {
            if (line.all {it == "x"}) return "x"
            if (line.all {it == "0"}) return "0"
        }
        return null
    }

    fun onCellClick(row: Int, col: Int){
        if (board[row][col].isEmpty() && winner == null){
            board[row][col] = currentPlayer
            winner = checkWinner()
            currentPlayer = if (currentPlayer == "x") "o" else "x"
        }
    }

    fun resetGame(){
        board = Array(3) {Array(3){""} }
        currentPlayer = "x"
        winner = null
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = winner?.let{"winner: $it  \uD83C\uDF89"} ?: "Current Player: $currentPlayer",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
    }

}