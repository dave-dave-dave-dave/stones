class Game {
    private val board = Board().also { println(it.pits.contentToString()) }
    private val player1 = Player("Player 1", 0..5).also { it.myTurn = true }
    private val player2 = Player("Player 2", 7..12)
    var gameOver = false

    fun play(){
        while (!gameOver){
            getMove()
        }
    }


    fun getMove(){
        val currentPlayer = getCurrentPlayer()
        println("${currentPlayer.name}, please enter your move (${currentPlayer.myPits}):")
        val input = readLine()
        val playedPit = input?.toIntOrNull()
        if(playedPit == null || !board.isValidMove(currentPlayer, playedPit)){
            println("Your input was invalid, please try again.")
            getMove()
        } else{
            playTurn(playedPit)
        }
    }

    fun playTurn(playedPit: Int) {
        var currentPlayer = getCurrentPlayer()
        println("current player is ${currentPlayer.name}")

        //if (currentPlayer.myTurn  && board.isValidMove(currentPlayer, playedPit)) {
            val lastPit = board.playMove(currentPlayer, playedPit)

            if (gameIsOver()) {
                // Game over logic

            } else if(lastPit == currentPlayer.bigPit){
                println("${currentPlayer.name} finished on his own big pit, gets another turn!")
            } else {
                println("Switching players...")
                switchTurns()
            }
    }



    private fun switchTurns() {
        player1.myTurn = !player1.myTurn
        player2.myTurn = !player2.myTurn
    }

    private fun getCurrentPlayer(): Player {
        return if (player1.myTurn) player1 else player2
    }

    fun gameIsOver() : Boolean{
        if(board.pits.slice(player1.myPits).sumOf { it } == 0 || board.pits.slice(player2.myPits).sumOf { it } == 0){
            println("GAME OVER")
            gameOver = true
        }
        return gameOver
    }
}