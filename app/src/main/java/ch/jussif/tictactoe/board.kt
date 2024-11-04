package ch.jussif.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random
import kotlinx.coroutines.*

class Board {
    val board = Array(3) { Array(3) { mutableStateOf(" ") } }
    var currentPlayer = mutableStateOf("X")
    var scorePlayer1: Int by mutableStateOf(0)
    var scorePlayer2: Int by mutableStateOf(0)

    fun makeMove(row: Int, col: Int): Boolean {
        // If it's not player X's turn, return immediately
        if (currentPlayer.value == "O" || isBoardFull() || checkWin(currentPlayer.value)) return false

        if (board[row][col].value == " ") {
            board[row][col].value = currentPlayer.value
            if (checkWin(currentPlayer.value)) {
                updateScore()
                return true
            }
            // Switch the current player
            currentPlayer.value = "O"

            CoroutineScope(Dispatchers.Main).launch {
                delay(1000) // delay for 2 seconds
                ComputerMove()
                if (checkWin(currentPlayer.value)) {
                    updateScore()
                } else {
                    currentPlayer.value = "X" // Switch back to the human player
                }
            }
            return true
        }
        return false
    }

    fun ComputerMove() {
        val availableSpots = mutableListOf<Pair<Int, Int>>()
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j].value == " ") {
                    availableSpots.add(Pair(i, j))
                }
            }
        }
        if (availableSpots.isNotEmpty()) {
            val (i, j) = availableSpots[Random.nextInt(availableSpots.size)]
            board[i][j].value = currentPlayer.value
        }
    }

    fun updateScore() {
        if (currentPlayer.value == "X") {
            scorePlayer1++
        } else {
            scorePlayer2++
        }
    }

    fun checkWin(player: String): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if (board[i][0].value == player && board[i][1].value == player && board[i][2].value == player) return true
            if (board[0][i].value == player && board[1][i].value == player && board[2][i].value == player) return true
        }
        // Check diagonals
        if (board[0][0].value == player && board[1][1].value == player && board[2][2].value == player) return true
        if (board[0][2].value == player && board[1][1].value == player && board[2][0].value == player) return true
        return false
    }

    fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j].value == " ") return false
            }
        }
        return true
    }

    fun reset() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].value = " "
            }
        }
        currentPlayer.value = "X"
    }

    fun resetScore(){
        scorePlayer1 = 0
        scorePlayer2 = 0
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].value = " "
            }
        }
        currentPlayer.value = "X"
    }

    fun get(row: Int, col: Int): String {
        return board[row][col].value
    }
}