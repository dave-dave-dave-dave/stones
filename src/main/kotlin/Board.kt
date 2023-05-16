class Board {
    private val PIT_COUNT = 14
    private val STONES_PER_PIT = 6
    private val bigPits = intArrayOf(6, 13)
    val pits = IntArray(PIT_COUNT) { i ->
        if (i in bigPits) 0 else STONES_PER_PIT
    }

    fun isValidMove(player: Player, pitIndex: Int) = pitIndex in player.myPits && pits[pitIndex] != 0

    fun sow(player: Player, playedPit: Int): Int {
        val stones = pits[playedPit]
        pits[playedPit] = 0 //take stones from selected pit, pit is now empty
        var pitIndex = playedPit
        //sow stones in next pits
        repeat(stones) {
            pitIndex = calculateNextPit(pitIndex, player)
            pits[pitIndex] += 1
        }
        return pitIndex
    }

    fun capture(player: Player, lastPit: Int) {
        val OPPOSING_PIT_SUM = 12 // Sum of two opposing pits
        if (lastPit in player.myPits && pits[lastPit] == 1) {
            val oppositePitIndex = OPPOSING_PIT_SUM - lastPit
            println("${player.name} captured ${pits[lastPit] + pits[oppositePitIndex]} stones!")
            pits[player.bigPit] += pits[oppositePitIndex] + 1 //add enemy stones and own stone to current player's big pit
            pits[oppositePitIndex] = 0 //empty captured enemy pit
            pits[lastPit] = 0 //empty own pit
        }
    }

    private fun calculateNextPit(currentPit: Int, player: Player): Int {
        var nextPit = (currentPit + 1) % pits.size
        if (nextPit in bigPits && nextPit != player.bigPit) {
            nextPit = (currentPit + 2) % pits.size
        }
        return nextPit
    }

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