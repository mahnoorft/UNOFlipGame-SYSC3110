# UNOFlipGame-SYSC3110
The UNO Flip Game project is an exciting digital version of the classic UNO card game with a twist. In this implementation, we've incorporated the unique UNO Flip rules (only light side for Milestone 2), adding a whole new level of fun and strategy to the game. Players can enjoy this engaging and competitive experience on their computers.


## Installation:

To get UNOFlipGame-SYSC3110 on your computer clone the repository using the following command:

git clone https://github.com/mahnoorft/UNOFlipGame-SYSC3110.git

To run the JUnit tests for the various classes, install the library for:
JUnit 5.8.1


## Usage
To run the UNOFlipGame, open the project repository and navigate to Main.java class. Click run, and enjoy!

Once, the Main.java is run the user will be prompted to enter the number of players for the UNO game. Each person will be able to see the hand of cards, the top card in the pile, a call UNO button, a draw card button, and an end turn button. 

## Features

### 1. UNO Flip Rules
- Embrace the exciting world of UNO Flip, where cards have two faces with distinct effects (for Milestone 2 only Light is present).
- Use Flip cards to change the game dynamics, keeping players on their toes.

### 2. Interactive Multiplayer
- Interactive GUI with buttons. Challenge friends or compete with players.

### 3. Traditional UNO Rules
- Enjoy classic UNO gameplay, including wild, draw cards, reverse, and skip actions.
- Switch between classic and UNO Flip modes to suit your preference.
- Once you have 1 card left in your hand, press the "call UNO" button before ending your turn or face the penalty!

These features and use cases make UNO Flip Game an enjoyable and versatile gaming experience for players of all backgrounds and skill levels.

### Milestone 2 Changes
- For this Milestone, we have implemented a user-friendly and engaging GUI using javax.swing framework.
- The UNOFlipGame project is based on a Model-View-Controller Pattern that allows user input to be handled efficiently.
- The MVC components communicate together using the Observer pattern (implemented with UNOGameHandler Interface and UNOGameEvent) which allows the view to subscribe to the model and update itsself once changs to the Model are made.
- The controller handles user input and calls the methods in the Model (UNOGameModel Class).

### Future Deliverables

Milestone 3:
- Uno Flip Integration: We will incorporate Uno Flip cards into the game, implementing their specific rules and scoring mechanisms.
- AI Player Capability: The game will now accommodate an arbitrary number of "AI" players

Milestone 4:
- Redo capabilities: Players will now have the ability to redo/ undo their moves within the game.
- Reply Capability: Players will now have the ability to replay the game, allowing them to restart and enjoy multiple rounds.
- Save/Load Features: We will implement a save and load system

## Author
- Mahnoor Fatima 101192353

## Contributors
Group 5:
- Areej Mahmoud 101218260
- Eric Cui 101237617
- Mahnoor Fatima 101192353
- Rama Alkhouli 101198025
