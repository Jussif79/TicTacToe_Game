package ch.jussif.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gameBoard = remember { mutableStateOf(Board()) }
            Players("Jussif", "Computer", gameBoard.value)
            Display(gameBoard.value)
        }
    }
}

@Composable
fun Display(gameBoard: Board) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 250.dp)
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0..2) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (j in 0..2) {
                    Button(
                        onClick = { gameBoard.makeMove(i, j) },
                        modifier = Modifier
                            .size(100.dp)
                            .padding(2.dp)
                            .border(2.dp, Color.Black, RectangleShape),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(gameBoard.get(i, j), fontSize = 40.sp)
                    }
                }
            }
        }
        GameStatus(gameBoard, gameBoard.currentPlayer.value)
        RestartButton(gameBoard)
        ResetScore(gameBoard)
    }
}

@Composable
fun Players(player1: String, player2: String, gameBoard: Board) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$player1 vs $player2",
            fontSize = 30.sp,
            color = Color.Black
        )
        Text(
            text = "Score:",
            Modifier.padding(top = 20.dp, bottom = 20.dp),
            fontSize = 30.sp,
            color = Color.Black
        )
        Text(
            text = "${gameBoard.scorePlayer1}                   ${gameBoard.scorePlayer2}",
            Modifier.padding(top = 20.dp),
            fontSize = 30.sp,
            color = Color.Black
        )
    }
}

@Composable
fun GameStatus(board: Board, player: String) {
    val gameStatus = if (board.checkWin(player)) {
        "Player ${board.currentPlayer.value} wins!"
    } else if (board.isBoardFull()) {
        "It's a tie!"
    } else {
        "Player ${board.currentPlayer.value}'s turn"
    }

    Text(
        text = gameStatus,
        modifier = Modifier.padding(30.dp),
        color = Color.Blue,
        fontSize = 20.sp
    )
}


@Composable
fun RestartButton(gameBoard: Board) {
    Button(
        onClick = { gameBoard.reset() },
        modifier = Modifier
            .padding(16.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.Green)
    ) {
        Text(text = "Restart Game", fontSize = 30.sp)
    }
}

@Composable
fun ResetScore(gameBoard: Board) {
    Button(
        onClick = { gameBoard.resetScore() },
        modifier = Modifier
            .padding(16.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.Blue)
    ) {
        Text(text = "Reset Score", fontSize = 30.sp)
    }
}
