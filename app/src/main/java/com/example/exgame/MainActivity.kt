package com.example.exgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var board = remember { mutableStateListOf(
        mutableStateListOf("", "", ""),
        mutableStateListOf("", "", ""),
        mutableStateListOf("", "", "")
        )}
    var currentPlayer = remember{ mutableStateOf("x") }
    currentPlayer.value ="0"
    var winner by remember { mutableStateOf<String?>(null) }
    winner.value ="x"

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
            winner.value = checkWinner()
            currentPlayer.value = if (currentPlayer.value == "x") "o" else "x"
        }
    }

    fun resetGame() {
        board.clear()
        board.addAll(
        listOf(
            mutableStateListOf("", "", ""),
            mutableStateListOf("", "", ""),
            mutableStateListOf("", "", "")
        )
        )
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

        Spacer(modifier = Modifier.height(20.dp))

        for (row in 0..2) {
            Row {
                for (col in 0..2){
                    Card(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .clickable { onCellClick(row, col) },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color.Black)
                    ){
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ){
                            Text(
                                text = board[row][col],
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (board[row][col] == "x") Color.Black else Color.Red
                            )
                        }

                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {resetGame()}) {
            Text(text = "Restart Game")
        }


    }

}