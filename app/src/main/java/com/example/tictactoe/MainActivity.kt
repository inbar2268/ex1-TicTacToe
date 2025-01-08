package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var cellsPositions = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    private var playerTurn = 1  //x-> 1, o-> 2
    private var totalTurns = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val cellsList = getCellsList()

        cellsList.forEachIndexed { index, cell ->
            cell?.setOnClickListener() {
                if (isCellEmpty(index)) {
                    playerTurn(cell as ImageButton, index)
                    cell.isEnabled = false
                }
            }
        }

        binding?.restartGameButton?.setOnClickListener(){restartGame()}

    }

    private fun getCellsList(): List<ImageButton?> {
        return listOf(binding?.button1, binding?.button2, binding?.button3, binding?.button4, binding?.button5, binding?.button6, binding?.button7, binding?.button8 ,binding?.button9)
    }

    private fun gameOver(){
        val cellsList = getCellsList()
        for(cell in cellsList){
            cell?.isEnabled = false
        }
        binding?.restartGameButton?.visibility = View.VISIBLE
    }


    private fun playerTurn(imageButton: ImageButton, cellNumber: Int) {
        Log.d("TAG", "totalTurns: ${totalTurns}")
        cellsPositions[cellNumber] = playerTurn

        if (playerTurn == 1) {
            imageButton.setImageResource(R.drawable.ximage)
        } else {
            imageButton.setImageResource(R.drawable.oimage)
        }
        cellsPositions[cellNumber] = playerTurn
        val winner = checkWin()
        if (winner == 1) {
            binding?.playerSymbol?.text = "Player X is the winner!"
            gameOver()
        } else if (winner == 2){
            binding?.playerSymbol?.text = "Player O is the winner!"
            gameOver()
        } else if (winner == 0 && totalTurns == 9){
            gameOver()
            binding?.playerSymbol?.text = "Its a tie!"
        } else{
            switchTurn()
            totalTurns += 1
        }
    }

    private fun switchTurn() {
        if (playerTurn == 1) {
            playerTurn = 2
            binding?.playerSymbol?.text = "Player: O"
        } else {
            playerTurn = 1
            binding?.playerSymbol?.text = "Player: X"
        }
    }

    private fun checkWin(): Int {
        for (i in 0..2) {
            val start = i * 3
            if (cellsPositions[start] == cellsPositions[start + 1] && cellsPositions[start + 1] == cellsPositions[start + 2] && cellsPositions[start] != 0) {
                return cellsPositions[start]
            }
        }

        for (i in 0..2) {
            if (cellsPositions[i] == cellsPositions[i + 3] && cellsPositions[i + 3] == cellsPositions[i + 6] && cellsPositions[i] != 0) {
                return cellsPositions[i]
            }
        }

        if (cellsPositions[0] == cellsPositions[4] && cellsPositions[4] == cellsPositions[8] && cellsPositions[0] != 0) {
            return cellsPositions[0]
        }
        if (cellsPositions[2] == cellsPositions[4] && cellsPositions[4] == cellsPositions[6] && cellsPositions[2] != 0) {
            return cellsPositions[2]
        }

        return 0 // no winner
    }

    private fun isCellEmpty(cellNumber: Int): Boolean{
        return if(cellsPositions[cellNumber] == 0){
            true
        } else{
            false
        }
    }

    fun restartGame(){
        cellsPositions = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        playerTurn = 1
        totalTurns = 1
        binding?.restartGameButton?.visibility = View.GONE
        binding?.playerSymbol?.text = "Player: X"
        val cellsList = getCellsList()
        for(cell in cellsList){
            cell?.isEnabled = true
            cell?.setImageResource(0)
        }
    }
}