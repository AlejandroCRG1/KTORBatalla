package com.example

class KnightsTour(private val boardSize: Int) {
    private val board = Array(boardSize) { IntArray(boardSize) }
    private val moves = arrayOf(
        intArrayOf(2, 1),
        intArrayOf(1, 2),
        intArrayOf(-1, 2),
        intArrayOf(-2, 1),
        intArrayOf(-2, -1),
        intArrayOf(-1, -2),
        intArrayOf(1, -2),
        intArrayOf(2, -1)
    )
    private val solutions = mutableListOf<Array<IntArray>>()

    fun solveTour(startX: Int, startY: Int): Boolean {
        board.forEach { it.fill(-1) }
        board[startX][startY] = 0
        solveTourUtil(startX, startY, 1)
        return solutions.isNotEmpty()
    }

    private fun solveTourUtil(x: Int, y: Int, moveCount: Int) {
        if (moveCount == boardSize * boardSize) {
            saveSolution()
            return
        }

        for (move in moves) {
            val nextX = x + move[0]
            val nextY = y + move[1]
            if (isSafe(nextX, nextY)) {
                board[nextX][nextY] = moveCount
                solveTourUtil(nextX, nextY, moveCount + 1)
                board[nextX][nextY] = -1 // Backtrack
            }
        }
    }

    private fun isSafe(x: Int, y: Int): Boolean {
        return x in 0 until boardSize && y in 0 until boardSize && board[x][y] == -1
    }

    private fun saveSolution() {
        val solution = Array(boardSize) { IntArray(boardSize) }
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                solution[i][j] = board[i][j]
            }
        }
        solutions.add(solution)
    }

    fun printSolutions() {
        if (solutions.isEmpty()) {
            println("No solutions found.")
        } else {
            for ((index, solution) in solutions.withIndex()) {
                println("Solution ${index + 1}:")
                for (i in 0 until boardSize) {
                    for (j in 0 until boardSize) {
                        print("${solution[i][j]} ")
                    }
                    println()
                }
                println()
            }
        }
    }
}

fun main() {
    val knightTour = KnightsTour(8)
    val startX = 0 // Change these coordinates as desired
    val startY = 0 // Change these coordinates as desired
    if (knightTour.solveTour(startX, startY)) {
        println("Knight's Tour solutions found:")
        knightTour.printSolutions()
    } else {
        println("No solution exists.")
    }
}
