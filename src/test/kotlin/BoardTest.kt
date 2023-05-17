import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BoardTest {

    @Mock
    private lateinit var playerManagerMock: PlayerManager

    @Mock
    private lateinit var player1Mock: Player

    @Mock
    private lateinit var player2Mock: Player

    @InjectMocks
    private lateinit var board: Board

    @BeforeEach
     fun setUp() {
        board = Board(playerManagerMock)
        `when`(playerManagerMock.currentPlayer).thenReturn(player1Mock)
        `when`(player1Mock.name).thenReturn("Player 1")
        `when`(player2Mock.name).thenReturn("Player 2")
        `when`(playerManagerMock.player1).thenReturn(player1Mock)
        `when`(playerManagerMock.player2).thenReturn(player2Mock)
        `when`(player1Mock.myPits).thenReturn(0..5)
        `when`(player2Mock.myPits).thenReturn(7..12)
        `when`(player1Mock.bigPit).thenReturn(6)
        `when`(player2Mock.bigPit).thenReturn(13)
    }

    @Test
    fun `isValidMove should return false if pit is empty`(){
        //arrange
        board.removeStonesFromPit(2)

        //act
        val result = board.isValidMove(2)

        //assert
        assertFalse(result)
    }

    @Test
    fun `isValidMove should return true if pit is in range and not empty`(){
        //act
        val result = board.isValidMove(2)

        //assert
        assertTrue(result)
    }

    @Test
    fun `capture should capture stones when conditions are met`() {
        // arrange
        //modifying the start board setup for easy capture
        board.removeStonesFromPit(1)
        board.addStonesToPit(1, 1)

        //act
        board.capture(1)

        //assert
        //captured pits should be empty, big pit should have the stones
        assertEquals(0, board.getPitStones(1))
        assertEquals(0, board.getPitStones(11))
        assertEquals(7, board.getPitStones(6))
    }

    @Test
    fun `areAllPitsEmpty should return true when board is empty`(){
        //arrange
        player1Mock.myPits.forEach { board.removeStonesFromPit(it) }

        //act
        val result = board.areAllPitsEmpty(player1Mock)

        //assert
        assertTrue(result)
    }

    @Test
    fun `areAllPitsEmpty should return false when board is not empty`(){
        //arrange
        player1Mock.myPits.forEach { board.removeStonesFromPit(it) }
        board.addStonesToPit(player1Mock.myPits.last, 1)

        //act
        val result = board.areAllPitsEmpty(player1Mock)

        //assert
        assertFalse(result)
    }

    @Test
    fun `determineWinner should correctly move all stones on the board to the big pits`() {
        //act
        board.determineWinner()
        val totalStones = board.getPitStones(player1Mock.bigPit) + board.getPitStones(player2Mock.bigPit)

        //assert
        assertEquals(72, totalStones)
    }

    @Test
    fun `sow should distribute stones correctly`() {
        //arrange
        val playedPit = 1

        //act
        val lastPit = board.sow(playedPit)

        //assert
        assertEquals(7, lastPit)
        assertEquals(6, board.getPitStones(0))
        assertEquals(0, board.getPitStones(1))
        assertEquals(7, board.getPitStones(2))
        assertEquals(7, board.getPitStones(3))
        assertEquals(7, board.getPitStones(4))
        assertEquals(7, board.getPitStones(5))
        assertEquals(1, board.getPitStones(6))
        assertEquals(7, board.getPitStones(7))
        assertEquals(6, board.getPitStones(8))
        assertEquals(6, board.getPitStones(9))
        assertEquals(6, board.getPitStones(10))
        assertEquals(6, board.getPitStones(11))
        assertEquals(6, board.getPitStones(12))
        assertEquals(0, board.getPitStones(13))
    }
}