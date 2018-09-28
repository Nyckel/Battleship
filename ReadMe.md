# Battleship

This is a custom two players Battleship game made with java and JavaFX for the UQAC's course "Advanced Oriented Object Project".


### Rules

The aim is the same than the classic game, you have to destroy all your opponent's ships, but the way to do it differs.

- Every player has five ships which have two parameters, a number of cells and a shooting range :
	1. Aircraft carrier (5,2)
	2. Cruiser (4,2)
	3. Destroyer (3,2)
	4. Submarine (3,4)
	5. Torpedo Boat (2,5)
	
- A shoot is determined by a vertical or an horizontal shooting range (determined by the position of the boat)
- After a missing shot, the opponent can move a ship (two cells max)
- If a ship is hit more than two times, it sinks


### Gameplay

After the start screen, the two players can place their ships on the grid. Then, the game really begin. Every round a player choose a target on the top grid (his ships are on the bottom grid). If he miss, the other player have the choice to move one of his ships. The last player to have ships afloat win.


### How to play ?

Just clone this repository and run it !

