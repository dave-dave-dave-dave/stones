class Player(val name : String, val myPits : IntRange) {
    var myTurn = false
    val bigPit = myPits.last + 1

    fun isMyPit(pitIndex : Int) = pitIndex in myPits
}