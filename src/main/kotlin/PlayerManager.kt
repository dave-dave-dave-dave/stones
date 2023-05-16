class PlayerManager(val player1: Player, val player2: Player) {
    var currentPlayer: Player = player1
        private set

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == player1) player2 else player1
    }
}