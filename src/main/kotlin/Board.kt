class Board(private val playerManager: PlayerManager) {
    private val PIT_COUNT = 14
    private val STONES_PER_PIT = 6
    private val bigPits = intArrayOf(6, 13)
    private val pits = IntArray(PIT_COUNT) { i ->
        if (i in bigPits) 0 else STONES_PER_PIT
    }

    fun getPitStones(pitIndex: Int): Int {
        return pits[pitIndex]
    }

    fun addStonesToPit(pitIndex: Int, stones: Int) {
        pits[pitIndex] += stones
    }

    fun removeStonesFromPit(pitIndex: Int) {
        pits[pitIndex] = 0
    }

    fun isValidMove(pitIndex: Int) = pitIndex in playerManager.currentPlayer.myPits && getPitStones(pitIndex) != 0

    fun sow(playedPit: Int): Int {
        val stones = getPitStones(playedPit)
        removeStonesFromPit(playedPit)
        var pitIndex = playedPit
        repeat(stones) {
            pitIndex = calculateNextPit(pitIndex)
            addStonesToPit(pitIndex, 1)
        }
        return pitIndex
    }

    fun capture(lastPit: Int) {
        val OPPOSING_PITS_INDEX_SUM = 12
        if (lastPit in playerManager.currentPlayer.myPits && getPitStones(lastPit) == 1) {
            val oppositePit = OPPOSING_PITS_INDEX_SUM - lastPit
            println("${playerManager.currentPlayer.name} captured ${getPitStones(lastPit) + getPitStones(oppositePit)} stones!")
            addStonesToPit(playerManager.currentPlayer.bigPit, getPitStones(oppositePit) + 1)
            removeStonesFromPit(oppositePit)
            removeStonesFromPit(lastPit)
        }
    }

    private fun calculateNextPit(currentPit: Int): Int {
        var nextPit = (currentPit + 1) % pits.size
        if (nextPit in bigPits && nextPit != playerManager.currentPlayer.bigPit) {
            nextPit = (currentPit + 2) % pits.size
        }
        return nextPit
    }

    fun areAllPitsEmpty(player: Player): Boolean {
        return player.myPits.all { getPitStones(it) == 0 }
    }

    fun determineWinner() {
        val player1 = playerManager.player1
        val player2 = playerManager.player2

        clearBoard(player1)
        clearBoard(player2)

        printBoard()

        val winner = when {
            getPitStones(player1.bigPit) > getPitStones(player2.bigPit) -> player1
            getPitStones(player2.bigPit) > getPitStones(player1.bigPit) -> player2
            else -> null // Tie
        }

        if (winner != null) {
            val loser = if (winner == player1) player2 else player1
            println("${winner.name} wins with a score of ${getPitStones(winner.bigPit)}!")
            println("${loser.name}'s score: ${getPitStones(loser.bigPit)}")
        } else {
            println("It's a tie! Both players have a score of ${getPitStones(player1.bigPit)}.")
        }
    }

    fun clearBoard(player : Player){
        val stones = player.myPits.sumOf{getPitStones(it)}
        player.myPits.forEach { removeStonesFromPit(it) }
        addStonesToPit(player.bigPit, stones)
    }

    fun printBoard() {
        val board = """
                        12      11      10       9       8       7
            _________________________________________________________________
            |       |  %3d  |  %3d  |  %3d  |  %3d  |  %3d  |  %3d  |       |
            |  %3d  |-------|-------|-------|-------|-------|-------|  %3d  |
            |       |  %3d  |  %3d  |  %3d  |  %3d  |  %3d  |  %3d  |       |
            ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾
                         0       1       2       3       4       5
            """.format(
            pits[12],
            pits[11],
            pits[10],
            pits[9],
            pits[8],
            pits[7],
            pits[13],
            pits[6],
            pits[0],
            pits[1],
            pits[2],
            pits[3],
            pits[4],
            pits[5]
        )
        println(board)
    }
}