package com.example.ex1
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var board: Array<Array<Button>>
    private var currentPlayer = 'X'
    private var gameEnded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = arrayOf(
            arrayOf(findViewById(R.id.button_00), findViewById(R.id.button_01), findViewById(R.id.button_02)),
            arrayOf(findViewById(R.id.button_10), findViewById(R.id.button_11), findViewById(R.id.button_12)),
            arrayOf(findViewById(R.id.button_20), findViewById(R.id.button_21), findViewById(R.id.button_22))
        )

        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].setOnClickListener { onCellClicked(board[i][j], i, j) }
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(button: Button, row: Int, col: Int) {
        if (button.text.isNotEmpty() || gameEnded) return

        button.text = currentPlayer.toString()

        if (checkWin(row, col)) {
            findViewById<TextView>(R.id.statusTextView).text = getString(R.string.winner_message, currentPlayer)
            gameEnded = true
            return
        }

        if (isBoardFull()) {
            findViewById<TextView>(R.id.statusTextView).text = getString(R.string.draw_message)
            gameEnded = true
            return
        }

        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
        findViewById<TextView>(R.id.statusTextView).text = getString(R.string.turn_message, currentPlayer)
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        if (board[row].all { it.text == currentPlayer.toString() })
            return true
        if ((0..2).all { board[it][col].text == currentPlayer.toString() })
            return true
        if (row == col && (0..2).all { board[it][it].text == currentPlayer.toString() })
            return true
        if (row + col == 2 && (0..2).all { board[it][2 - it].text == currentPlayer.toString() })
            return true
        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it.text.isNotEmpty() } }
    }

    private fun resetGame() {
        for (row in board) {
            for (button in row) {
                button.text = ""
            }
        }
        currentPlayer = 'X'
        gameEnded = false
        findViewById<TextView>(R.id.statusTextView).text = getString(R.string.turn_message, currentPlayer)
    }

}
