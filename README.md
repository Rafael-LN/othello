# Othello Game Infrastructure

## Objective
The objective of this university project is to consolidate and apply knowledge about distributed computer infrastructures, specifically focusing on the design of architectures and protocols involved in communication through a computer network.

## Context
Othello is a strategic game for two players (Black and White), played on an 8x8 board. The game begins with four pieces placed in the center of the board. The player with the black pieces makes the first move. Each player places their new piece on the board so that there is at least one or more opponent's pieces adjacent in the line (horizontal, vertical, or diagonal) formed between the new piece and one of their own pieces on the board. After placing the new piece, a player captures all opponent's pieces existing in the straight lines between that piece and any other of their own pieces on the board. All captured pieces assume the color of the new piece.
Players take turns. If a player does not have a valid move, they pass the turn to the opponent. When neither player has a valid move, the game ends. A game of Othello can end before all squares on the board are occupied. The player with more pieces on the board at the end of the game wins. If both players have the same number of pieces, it's a draw.

## Requirements
Through practical group work (2 or 3 members), the goal is to design and implement a computational infrastructure (computers connected in a network) that allows registered players to play Othello.
A player registers themselves by providing a nickname, a password, nationality, age, and a photo that can be changed later. Additionally, records of wins, losses, and time spent on each game should be maintained for each player.

## Implementation
The project will involve creating a Java application that implements the Othello game logic, networking functionalities, and user interface for player registration and gameplay. The game will be played over a network where players can connect, register, and play against each other.

## Contributors
- [Rafael Nunes](https://github.com/Rafael-LN)
- [Tatiana Cristão](https://github.com/TatianaCristao)
- [João Costa](https://github.com/joao1904)
