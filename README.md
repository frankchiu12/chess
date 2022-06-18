# chess

This is a version of the classic game of Chess with all the rules of the game staying consistent. If you don't know how to play Chess, you could find more details at https://sakkpalota.hu/index.php/en/chess/rules or any other website.

In terms of functionality:
1. To start the game, you must press the button "Start" or "P" on the keyboard, at which point the timer would start.
2. If you click on a ChessPiece, the ChessPiece you click on would be highlighted yellow. If there are possible moves available, they would be highlighted green, and any move that eats the opponent would be highlighted red.
3. Each time you move, the board would rotate to the perspective of the opponent (there is also an indicator of whose move it is).
4. Pressing "R" reverses the previous move.
5. There would be visual effects for check and checkmate, with the piece checking being highlighted yellow and the piece being checked/checkmated being highlighted red.
6. There is a textField on the side. There, you could enter the ChessPiece you want a Pawn to be promoted to. Then, you press the button "Submit." Note that the entry must be capitalized. If this field is blank, the default is to promote to a Queen.
7. Once a player is checkmated, the opponent wins.
8. If a player moves into a checkmate, that player automatically loses.
9. The default timer is 15 minutes. If any player's timer hits 0, the opponent wins.
10. If there are any questions, the errorMessageLabel on the right panel is helpful.


In addition to the buttons:
1. Pressing "S" restarts the game.
2. Pressing "Q" quits the game.
