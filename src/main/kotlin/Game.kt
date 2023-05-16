class Game {
    private val board = Board()
    private val player1 = Player("Player 1", 0..5)
    private val player2 = Player("Player 2", 7..12)

    init {
        player1.myTurn = true
        board.printBoard()
    }

    fun play() {
        println("GAME START")
        while (!isGameOver()) {
            getMove()
        }
        println("GAME OVER")
    }

    private fun getMove() {
        val currentPlayer = getCurrentPlayer()
        println("${currentPlayer.name}, please enter your move (${currentPlayer.myPits}):")
        val input = readlnOrNull()
        val playedPit = input?.toIntOrNull()

        if (playedPit == null || !board.isValidMove(currentPlayer, playedPit)) {
            println("Your input was invalid, please try again.")
            getMove()
        } else {
            playTurn(playedPit)
        }
    }


    fun playTurn(playedPit: Int) {
        val currentPlayer = getCurrentPlayer()
        println("current player is ${currentPlayer.name}")

        val lastPit = board.sow(currentPlayer, playedPit)
        board.capture(currentPlayer, lastPit)
        board.printBoard()

        if(isGameOver()){
            return
        }
        handleTurnEnd(lastPit, currentPlayer)
    }

    private fun handleTurnEnd(lastPit: Int, currentPlayer: Player) {
        if (lastPit == currentPlayer.bigPit) {
            println("${currentPlayer.name} finished on his own big pit, gets another turn!")
        } else {
            println("Switching players...")
            switchTurns()
        }
    }

    private fun switchTurns() {
        player1.myTurn = player2.myTurn.also { player2.myTurn = player1.myTurn }
    }

    private fun getCurrentPlayer(): Player {
        return if (player1.myTurn) player1 else player2
    }

    private fun isGameOver(): Boolean {
        return (board.pits.slice(player1.myPits).sumOf { it } == 0 ||
                board.pits.slice(player2.myPits).sumOf { it } == 0)
    }
}