class Board {
    val bigPits = intArrayOf(6, 13)
    val pits = IntArray(14) { i ->
        if (i in bigPits) 0 else 6
    }

    fun isValidMove(player: Player, pitIndex: Int) = pitIndex in player.myPits && pits[pitIndex] != 0

    fun playMove(player: Player, playedPit: Int): Int {
        val stones = pits[playedPit]
        pits[playedPit] = 0 //take stones from selected pit, pit is now empty
        var currentPit = playedPit
        //sow stones in next pits
        repeat(stones) {
            currentPit = calcNextPit(currentPit)
            if (currentPit in bigPits && currentPit != player.bigPit) {
                currentPit = calcNextPit(currentPit)
            }
            pits[currentPit] += 1
        }
        capture(player, currentPit)
        printBoard()
        return currentPit
    }

    fun capture(player: Player, lastPit: Int) {
        val oppositePitIndex = 12 - lastPit
        if (lastPit in player.myPits && pits[lastPit] == 1 && pits[oppositePitIndex] != 0) {
            println("${player.name} captured ${pits[oppositePitIndex]} stones!")
            pits[player.bigPit] += pits[oppositePitIndex] + 1 //add enemy stones and own stone to current player's big pit
            pits[oppositePitIndex] = 0 //empty captured enemy pit
            pits[lastPit] = 0 //empty own pit
        }
    }

    private fun calcNextPit(i: Int) = (i + 1) % pits.size

    fun printBoard() {
        val board = """
            _________________________________________________________________
            |       |  %3d  |  %3d  |  %3d  |  %3d  |  %3d  |  %3d  |       |
            |  %3d  |-------|-------|-------|-------|-------|-------|  %3d  |
            |       |  %3d  |  %3d  |  %3d  |  %3d  |  %3d  |  %3d  |       |
            ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾
            """.format(pits[12], pits[11], pits[10], pits[9], pits[8], pits[7],pits[13], pits[6], pits[0], pits[1], pits[2], pits[3], pits[4], pits[5])
        println(board)
    }

}