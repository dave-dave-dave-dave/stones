# stones

Coding challenge

## Rules

### Stones Game
The goal is for you to showcase your programming skills. It's not so much about finishing and solving the
problem, but delivering a well designed solution and code that you find qualitative. Try not to spend more
than 8 hours in completing the assessment. We asses unit testing, clean coding, code complexity. Some
rules may be ambiguous but that is intentional.
We would like you to program a Java/Kotlin application using frameworks you are familiar with, that runs a
game. The rules are explained below.
This application should allow 2 players to play the game (no AI required). It doesn't need a fancy web
interface, but whatever you think is nice to show your programming skills, is up to you.

### Board Setup
Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger
pit. In each of the six round pits are put six stones when the game starts.

### Game Play
The player who begins with the first move picks up all the stones in anyone of his own six pits, and sows the
stones on to the right, one in each of the following pits, including his own big pit. No stones are put in the
opponents' big pit. If the player's last stone lands in his own big pit, he gets another turn. This can be
repeated several times before it's the other player's turn.
### Capturing Stones
During the game the pits are emptied on both sides. Always when the last stone lands in an own empty pit,
the player captures his own stone and all stones in the opposite pit (the other players' pit) and puts them in
his own pit.

### The Game Ends
The game is over as soon as one of the sides run out of stones. The player who still has stones in his pits
keeps them and puts them in his/hers big pit. Winner of the game is the player who has the most stones in
his big pit.
