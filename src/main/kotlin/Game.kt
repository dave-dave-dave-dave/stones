class Game {
    private val player1 = Player("Player 1", 0..5)
    private val player2 = Player("Player 2", 7..12)
    private val playerManager = PlayerManager(player1, player2)
    private val board = Board(playerManager)

    fun play() {
        println("GAME START")
        board.printBoard()
        while (!isGameOver()) {
            promptForMove()
        }
        println("GAME OVER")
    }

    private fun promptForMove() {
        println("${playerManager.currentPlayer.name}, please enter your move (${playerManager.currentPlayer.myPits}):")
        val input = readlnOrNull()
        val playedPit = input?.toIntOrNull()

        if (playedPit == null || !board.isValidMove(playedPit)) {
            println("Your input was invalid, please try again.")
            promptForMove()
        } else {
            playTurn(playedPit)
        }
    }


    private fun playTurn(playedPit: Int) {
        val lastPit = board.sow(playedPit)
        board.capture(lastPit)
        board.printBoard()

        if (isGameOver()) {
            board.determineWinner()
            return
        }
        handleTurnEnd(lastPit)
    }

    private fun handleTurnEnd(lastPit: Int) {
        if (lastPit == playerManager.currentPlayer.bigPit) {
            println("${playerManager.currentPlayer.name} finished on his own big pit, gets another turn!")
        } else {
            playerManager.switchPlayer()
        }
    }

    private fun isGameOver(): Boolean {
        return board.areAllPitsEmpty(player1) || board.areAllPitsEmpty(player2)
    }
}