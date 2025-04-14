


import java.util.ArrayList;
import processing.core.PApplet;

/**
 * The CaboGame class implements the main game logic for the card game CABO. It manages the deck,
 * discard pile, players, game state, and user interactions.
 */
// ////// do i need to do the processing and extending step here ??
public class CaboGame extends PApplet {


  /**
   * Enum representing the different action states in the game (e.g., swapping cards, peeking,
   * spying, switching).
   * 
   * This allows us to easily restrict the possible values of a variable.
   */
  private enum ActionState {
    NONE, SWAPPING, PEEKING, SPYING, SWITCHING
  }

  private ActionState actionState = ActionState.NONE;
  private Deck deck; // The main deck of cards
  private boolean gameOver; // Flag to indicate if the game is over
  private int selectedCardFromCurrentPlayer;
  private Deck discard;
  private BaseCard drawnCard;
  private Player[] players; // Array to hold players
  private int currentPlayer; // Index of the current player
  private int caboPlayer; // Index of player who has declared CABO
  private Button[] buttons;


  // provided data fields for tracking the players' moves through the game
  private ArrayList<String> gameMessages = new ArrayList<>();

  /**
   * Launch the game window; PROVIDED. Note: the argument to PApplet.main() must match the name of
   * this class, or it won't run!
   * 
   * @param args unused
   */
  public static void main(String[] args) {
    PApplet.main("CaboGame");
  }

  /**
   * Sets up the initial window size for the game; PROVIDED.
   */
  @Override
  public void settings() {
    size(1000, 800);
  }

  /**
   * Sets up the game environment, including the font, game state, and game elements.
   */
  @Override
  public void setup() {
    textFont(createFont("Arial", 16));

    // TODO: setProcessing for the classes which require it
    BaseCard.setProcessing(this); // Set the processing environment for BaseCard
    Deck.setProcessing(this); // Set the processing environment for Deck
    Button.setProcessing(this);



    // TODO: set up deck and discard pile

    ArrayList<BaseCard> deckList = Deck.createDeck();
    Deck deck1 = new Deck(deckList);
    this.deck = deck1;
    ArrayList<BaseCard> discardList = new ArrayList<>();
    Deck discardPile = new Deck(discardList);
    this.discard = discardPile;
    drawnCard = null;

    // TODO: set up players array and deal their cards
    players = new Player[4];
    players[0] = new Player("Cyntra", 0, false);
    players[1] = new AIPlayer("Avalon", 1, true);
    players[2] = new AIPlayer("Balthor", 2, true);
    players[3] = new AIPlayer("Ophira", 3, true);
    currentPlayer = 0;
    caboPlayer = -1;
    setGameStatus("Turn for " + players[currentPlayer].getName());
    for (int i = 0; i < 4; i++) {
      for (Player player : players) {
        BaseCard dealtCard = deck.drawCard();
        if (dealtCard != null) {
          player.addCardToHand(dealtCard);
        } else {
          System.out.println("Deck is empty. Unable to deal further cards.");
        }
      }
    }
    for (int i = 0; i < 4; i++) {
      if (!players[i].isComputer()) {
        players[i].getHand().setFaceUp(0, true);
        players[i].getHand().setFaceUp(1, true);
      }
    }


    // TODO: set up buttons and update their states for the beginning of the game
    buttons = new Button[5];
    addButtons(buttons);

    updateButtonStates();
    selectedCardFromCurrentPlayer = -1;

    // TODO: update the gameMessages log: "Turn for "+currentPlayer.name
  }

  /**
   * Helper method for adding buttons to the buttons array in setup
   * 
   * @param buttons - array in setup that needs to be populated
   */
  private void addButtons(Button[] buttons) {
    buttons[0] = new Button("Draw from Deck", 50, 700, 150, 40);
    buttons[1] = new Button("Swap a Card", 220, 700, 150, 40);
    buttons[2] = new Button("Declare Cabo", 390, 700, 150, 40);
    buttons[3] = new Button("Use Action", 390 + 170, 700, 150, 40);
    buttons[4] = new Button("End Turn", 390 + 170 + 170, 700, 150, 40);
  }

  /**
   * Console-only output for verifying the setup of the card objects and the deck containing them
   */
  public void deckCheck() {
    ArrayList<BaseCard> deckList = Deck.createDeck();
    Deck deck = new Deck(deckList);
    // TODO: verify that there are 52 cards in the deck
    if (deck.size() != 52) {
      System.out.println("Deck size is incorrect: " + deck.size());
    } else {
      System.out.println("Deck size is correct: 52 cards.");
    }

    // TODO: verify that there are 8 of each type of ActionCard
    int peekCardCount = 0;
    int spyCardCount = 0;
    int switchCardCount = 0;

    for (BaseCard card : deck.createDeck()) {
      if (card instanceof ActionCard) {
        String actionType = ((ActionCard) card).getActionType(); // Cast to ActionCard
        if (actionType.equals("peek")) {
          peekCardCount++;
        } else if (actionType.equals("spy")) {
          spyCardCount++;
        } else if (actionType.equals("switch")) {
          switchCardCount++;
        }
      }
    }
    boolean checker = true;
    if (switchCardCount != 8) {
      checker = false;
    }
    if (peekCardCount != 8) {
      checker = false;
    }
    if (spyCardCount != 8) {
      checker = false;
    }
    if (checker == true) {
      System.out.println("This is the correct action card number");
    } else {
      System.out.println("This is not the correct action card number");
    }

    // TODO: verify that there are 13 of each suit
    int heartsCount = 0;
    int diamondsCount = 0;
    int clubsCount = 0;
    int spadesCount = 0;

    for (BaseCard card : deck.createDeck()) {
      switch (card.suit) {
        case "Hearts":
          heartsCount++;
          break;
        case "Diamonds":
          diamondsCount++;
          break;
        case "Clubs":
          clubsCount++;
          break;
        case "Spades":
          spadesCount++;
          break;
      }
    }

    if (diamondsCount != 13) {
      System.out.println("Diamonds count is incorrect: " + diamondsCount);
    } else {
      System.out.println("Diamonds count is correct: 13 cards.");
    }

    if (clubsCount != 13) {
      System.out.println("Clubs count is incorrect: " + clubsCount);
    } else {
      System.out.println("Clubs count is correct: 13 cards.");
    }

    if (spadesCount != 13) {
      System.out.println("Spades count is incorrect: " + spadesCount);
    } else {
      System.out.println("Spades count is correct: 13 cards.");
    }
    if (heartsCount != 13) {
      System.out.println("Hearts count is incorrect: " + heartsCount);
    } else {
      System.out.println("Hearts count is correct: 13 cards.");
    }
    // TODO: verify that the king of diamonds' getRank() returns -1
    for (BaseCard card : deck.createDeck()) {
      if (card.getRank() == -1 && "Diamonds".equals(card.suit)) {
        System.out.println("King of Diamonds has the correct rank: -1");
        break;
      }
    }
  }

  /**
   * Updates the state of the action buttons based on the current game state. Activates or
   * deactivates buttons depending on whether it's the start of a player's turn, a card has been
   * drawn, or the player is an AI.
   */
  public void updateButtonStates() {
    // TODO: if the current player is a computer, deactivate all buttons
    if (players[currentPlayer].isComputer()) {
      for (int i = 0; i < buttons.length; i++) {
        buttons[i].setActive(false);
      }
      // TODO: otherwise, if no card has been drawn, activate accordingly (see writeup)
    } else if (drawnCard == null) { // WILL PROBABLY NEED TO CHANGE
      buttons[0].setActive(true);
      buttons[2].setActive(caboPlayer == -1);

      buttons[1].setActive(false); // Swap a Card button
      buttons[3].setActive(false); // Use Action button
      buttons[4].setActive(false);
      // TODO: otherwise, if a card has been drawn, activate accordingly (see writeup)
    } else {
      buttons[0].setActive(false);
      buttons[1].setActive(true);
      buttons[2].setActive(false);
      buttons[4].setActive(true);

      if (drawnCard instanceof ActionCard) {
        ActionCard actionCard = (ActionCard) drawnCard;
        buttons[3].setActive(true);
        buttons[3].setLabel(actionCard.getActionType().toUpperCase());
      } else {
        buttons[3].setActive(false);
      }
    }
  }

  /**
   * Renders the graphical user interface; also handles some game logic for the computer players.
   */
  @Override
  public void draw() {

    background(0, 128, 0);
    // TODO: draw the deck and discard pile
    this.deck.draw(500, 80, false);
    this.discard.draw(600, 80, true);
    textSize(16);
    fill(255);
    text("Deck:", 520, 60);
    text("Discard Pile:", 644, 60);

    // TODO: draw the players' hands
    drawPlayerNamesAndHands();
    // TODO: draw the buttons
    if (!players[currentPlayer].isComputer()) {
      for (Button button : buttons) {
        button.draw();
      }
    }
    // TODO: show the drawn card, if there is one
    if (drawnCard != null) {
      drawnCard.setFaceUp(true);
      drawnCard.draw(500, 500);
    }

    // TODO: if the game is over, display the game over status
    // Display game messages with different colors based on the content
    int y = 200; // Starting y-position for messages
    for (String message : gameMessages) {
      textSize(16);
      if (message.contains("CABO")) {
        fill(255, 128, 0);
      } else if (message.contains("switched")) {
        fill(255, 204, 153);
      } else if (message.contains("spied")) {
        fill(255, 229, 204);
      } else {
        fill(255);
      }
      text(message, width - 300, y); // Adjust x-position as needed
      y += 20; // Spacing between messages
    }
    // TODO: handle the computer players' turns
    if (!gameOver && players[currentPlayer].isComputer()) {
      performAITurn();
    }

    if (gameOver) {
      displayGameOver();
    }
  }

  /**
   * 
   */
  private void drawPlayerNamesAndHands() {
    textSize(16);
    fill(255);

    for (int i = 0; i < 4; i++) {
      text(players[i].getName(), 50, 45 + 150 * i);
      players[i].getHand().draw(60 + 150 * i);
    }
  }

  /**
   * Handles mouse press events during the game. It manages user interactions with buttons (that is,
   * drawing a card, declaring CABO, swapping cards, using action cards) and updates the game state
   * accordingly.
   */
  @Override
  public void mousePressed() {
    // TODO: if game is over or it's the computer's turn, do nothing
    if (gameOver == true || players[currentPlayer].isComputer()) {
      return;
    }
    // TODO: handle button clicks
    try {
      if (buttons[0].isActive() && buttons[0].isMouseOver()) {
        drawFromDeck();
      }
      if (buttons[1].isActive() && buttons[1].isMouseOver()) {
        actionState = ActionState.SWAPPING;
        setGameStatus("Click a card in your hand to swap it with the drawn card.");

      }
      if (buttons[2].isActive() && buttons[2].isMouseOver()) {
        declareCabo();
      }
      if (buttons[3].isActive() && buttons[3].isMouseOver()) {
        buttons[3].setLabel("Use Action");
        String actionType = "";
        if (drawnCard instanceof ActionCard) {
          ActionCard actionCard = (ActionCard) drawnCard;
          actionType = actionCard.getActionType();
        }

        switch (actionType) {
          case "peek":
            actionState = ActionState.PEEKING;
            setGameStatus("Click a card in your hand to peek at it.");
            break;
          case "spy":
            actionState = ActionState.SPYING;
            setGameStatus("Click a card in another player's hand to spy on it.");
            break;
          case "switch":
            actionState = ActionState.SWITCHING;
            setGameStatus(
                "Click a card from your hand, then a card from another Kingdom's hand to switch.");
            break;
          default:
            setGameStatus("Unknown action. Please try again.");
            break;
        }
      }

      if (buttons[4].isActive() && buttons[4].isMouseOver()) {
        nextTurn();
      }
      System.out.println(actionState);
      // handle additional action states (TODO: complete these methods)
      switch (actionState) {
        case SWAPPING -> handleCardSwap();
        case PEEKING -> handlePeek();
        case SPYING -> handleSpy();
        case SWITCHING -> handleSwitch();
        default -> {
          /* No action to be taken */ }
      }
      System.out.println(actionState);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  ///////////////////////////////////// BUTTON CLICK HANDLERS /////////////////////////////////////

  /**
   * Handles the action of drawing a card from the deck. If the deck is empty, the game ends.
   * Otherwise, the drawn card is displayed in the middle of the table. The game status and button
   * states are updated accordingly.
   */
  public void drawFromDeck() {
    // TODO: if the deck is empty, game over
    if (deck.size() == 0) {
      gameOver = true;
      setGameStatus("The deck is empty. Game Over!");
      displayGameOver();
      return;
    }
    // TODO: otherwise, draw the next card from the deck
    drawnCard = deck.drawCard();
    if (drawnCard != null) {
      setGameStatus(players[currentPlayer].getName() + " drew a card.");
    }
    // TODO: update the gameMessages log: player.name+" drew a card."
    // TODO: update the button states
    updateButtonStates();
  }

  /**
   * Handles the action of declaring CABO. Updates the game status to show that the player has
   * declared CABO.
   */
  public void declareCabo() {
    // TODO: update the gameMessages log: player.name+" declares CABO!"
    setGameStatus(players[currentPlayer].getName() + " declares CABO!");
    // TODO: set the caboPlayer to the current player's index
    caboPlayer = currentPlayer;
    // TODO: end this player's turn
    nextTurn();
  }

  ///////////////////////////////////// ACTION STATE HANDLERS /////////////////////////////////////

  /**
   * This method runs when the human player has chosen to SWAP the drawn card with one from their
   * hand. Detect if the mouse is over a card from the currentPlayer's hand and, if it is, swap the
   * drawn card with that card.
   * 
   * If the mouse is not currently over a card from the currentPlayer's hand, this method does
   * nothing.
   */
  public void handleCardSwap() {
    // TODO: find a card from the current player's hand that the mouse is currently over
    int idxMouseOver = players[currentPlayer].getHand().indexOfMouseOver();
    if (idxMouseOver == -1) {
      // No card was hovered over, so return without performing any swap
      return;
    }

    // TODO: swap that card with the drawnCard
    BaseCard swappedCard = players[currentPlayer].getHand().swap(drawnCard, idxMouseOver);
    // TODO: add the swapped-out card from the player's hand to the discard pile
    discard.addCard(swappedCard);
    // TODO: update the gameMessages log: "Swapped the drawn card with card "+(index+1)+" in the
    // hand."
    setGameStatus("Swapped the drawn card with card " + (idxMouseOver + 1) + " in the hand.");
    // TODO: set the drawnCard to null and the actionState to NONE
    drawnCard = null;
    actionState = ActionState.NONE;
    // TODO: set all buttons except End Turn to inactive
    for (int i = 0; i < 4; i++) {
      buttons[i].setActive(false);
    }

    // TODO: uncomment this code to erase all knowledge of the card at that index from the AI

    AIPlayer AI;
    for (int j = 1; j < players.length; ++j) {
      AI = (AIPlayer) players[j];
      AI.setCardKnowledge(0, idxMouseOver, false);
    }

  }

  /**
   * Handles the action of peeking at one of your cards. The player selects a card from their own
   * hand, which is then revealed (set face-up).
   * 
   * If the mouse is not currently over a card from the currentPlayer's hand, this method does
   * nothing.
   */
  public void handlePeek() {
    // TODO: find a card from the current player's hand that the mouse is currently over
    int idxMouseOver = players[currentPlayer].getHand().indexOfMouseOver();
    if (idxMouseOver == -1) {
      return;
    }
    // TODO: set that card to be face-up
    players[currentPlayer].getHand().setFaceUp(idxMouseOver, true);
    // TODO: update the gameMessages log: "Revealed card "+(index+1)+" in the hand."
    setGameStatus("Revealed card " + (idxMouseOver + 1) + " in the hand.");
    // TODO: add the drawnCard to the discard, set drawnCard to null and actionState to NONE
    discard.addCard(drawnCard);
    drawnCard = null;
    actionState = ActionState.NONE;
    // TODO: set all buttons except End Turn to inactive
    for (int i = 0; i < 4; i++) {
      buttons[i].setActive(false);
    }
  }

  /**
   * Handles the spy action, allowing the current player to reveal one of another player's cards.
   * The current player selects a card from another player's hand, which is temporarily revealed.
   * 
   * If the mouse is not currently over a card from another player's hand, this method does nothing.
   */
  public void handleSpy() {
    // TODO: find a card from any player's hand that the mouse is currently over
    int idxMouseOver = -1;
    Player victim = null;
    for (int i = 0; i < players.length; i++) {
      idxMouseOver = players[i].getHand().indexOfMouseOver();
      if (!(idxMouseOver == -1)) {
        // TODO: if it is not one of their own cards, set it to be face-up
        if (i != currentPlayer) {
          victim = players[i];
          players[i].getHand().setFaceUp(idxMouseOver, true);
          setGameStatus("Spied on " + victim.getName() + "'s card.");
          discard.addCard(drawnCard);
          drawnCard = null;
          actionState = ActionState.NONE;
          // TODO: set all buttons except End Turn to inactive
          for (int j = 0; j < 4; j++) {
            buttons[j].setActive(false);
          }
          break;
        }
      }
    }
  }

  // TODO: update the gameMessages log: "Spied on "+player.name+"'s card.";

  // TODO: add the drawnCard to the discard, set drawnCard to null and actionState to NONE



  /**
   * Handles the switch action, allowing the current player to switch one of their cards with a card
   * from another player's hand.
   * 
   * This action is performed in 2 steps, in this order: (1) select a card from the current player's
   * hand (2) select a card from another player's hand
   * 
   * If the mouse is not currently over a card, this method does nothing.
   */
  public void handleSwitch() {
    // TODO: add CaboGame instance variable to store the index of the card from the currentPlayer's
    // hand
    int hoveredCardIndex = -1;
    int otherPlayerCardIndex = -1;
    int otherPlayerIndex = -1;

    // Check if the player has already selected a card from their own hand
    if (selectedCardFromCurrentPlayer == -1) {
      // Player hasn't selected their own card yet, so determine if the mouse is over a card in
      // their own hand
      hoveredCardIndex = players[currentPlayer].getHand().indexOfMouseOver();
      if (hoveredCardIndex != -1) {
        // Store the selected card index
        selectedCardFromCurrentPlayer = hoveredCardIndex;
        setGameStatus(
            "Selected a card from your hand. Now select a card from another player's hand to switch.");
        return;
      } else {
        // No card was hovered over
        setGameStatus("Please select a card from your hand first.");
        return;
      }
    }

    // Player has already selected a card from their own hand, so look at other players' hands
    for (int i = 0; i < players.length; i++) {
      if (i != currentPlayer) { // Only look at other players' hands
        otherPlayerCardIndex = players[i].getHand().indexOfMouseOver();
        if (otherPlayerCardIndex != -1) {
          // Found a card from another player's hand to switch with
          otherPlayerIndex = i;
          break;
        }
      }
    }

    // Validate that a card was selected from another player's hand
    if (otherPlayerIndex == -1 || otherPlayerCardIndex == -1) {
      setGameStatus("Please select a card from another player's hand to complete the switch.");
      return;
    }

    // Swap the selected card with the card from the other player's hand
    players[currentPlayer].getHand().switchCards(selectedCardFromCurrentPlayer,
        players[otherPlayerIndex].getHand(), otherPlayerCardIndex);

    // Update the gameMessages log
    setGameStatus("Switched a card with " + players[otherPlayerIndex].getName());
    discard.addCard(drawnCard);
    drawnCard = null;
    actionState = ActionState.NONE;

    // Deactivate buttons except for End Turn
    for (int j = 0; j < 4; j++) {
      buttons[j].setActive(false);
    }

    // Update the knowledge of the swapped card for the AI player (uncommented from the provided
    // code)
    boolean knowledge = ((AIPlayer) players[otherPlayerIndex]).getCardKnowledge(otherPlayerIndex,
        otherPlayerCardIndex);
    ((AIPlayer) players[otherPlayerIndex]).setCardKnowledge(otherPlayerIndex, otherPlayerCardIndex,
        ((AIPlayer) players[otherPlayerIndex]).getCardKnowledge(currentPlayer,
            selectedCardFromCurrentPlayer));
    ((AIPlayer) players[otherPlayerIndex]).setCardKnowledge(currentPlayer,
        selectedCardFromCurrentPlayer, knowledge);

    // Reset the selected card instance variable
    selectedCardFromCurrentPlayer = -1;
  }


  /////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Advances the game to the next player's turn. Hides all players' cards, updates the current
   * player, checks for game-over conditions, resets action states, and updates the UI button states
   * for the new player's turn.
   */
  public void nextTurn() {
    // TODO: hide all players' cards
    for (Player player : players) {
      for (int i = 0; i < player.getHand().size(); i++) {
        player.getHand().setFaceUp(i, false); // Hide all cards by setting them face-down
      }
    }
    // TODO: if there is still an active drawnCard, discard it and set drawnCard to null
    if (drawnCard != null) {
      discard.addCard(drawnCard);
      drawnCard = null;
    }
    // TODO: advance the current player to the next one in the list
    currentPlayer = (currentPlayer + 1) % players.length;
    // TODO: check if the new player is the one who declared CABO (and end the game if so)
    if (currentPlayer == caboPlayer) {
      gameOver = true;
      displayGameOver();
      return; // End the turn and game if the CABO player is up again
    }
    // TODO: update the gameMessages log: "Turn for "+player.name
    setGameStatus("Turn for " + players[currentPlayer].getName());
    // TODO: reset the action state to NONE
    actionState = ActionState.NONE;
    // TODO: update the button states
    updateButtonStates();
  }

  /**
   * Displays the game-over screen and reveals all players' cards. The method calculates each
   * player's score, identifies the winner, and displays a message about the game's result,
   * including cases where there is no winner.
   * 
   * We've provided the code for the GUI parts, but the logic behind this method is still TODO
   */
  public void displayGameOver() {
    // Create a dimmed background overlay
    fill(0, 0, 0, 200);
    rect(0, 0, width, height);
    fill(255);
    textSize(32);
    textAlign(CENTER, CENTER);
    text("Game Over!", (float) width / 2, (float) height / 2 - 150);

    // TODO: reveal all players' cards
    int yPosition = height / 2 - 100;
    textSize(24);
    int lowestScore = Integer.MAX_VALUE;
    Player winner = null;
    boolean tie = false;

    // Calculate scores and find the player(s) with the lowest score
    for (Player player : players) {
      int playerScore = player.getHand().calcHand();
      text(player.getName() + "'s score: " + playerScore, (float) width / 2, yPosition);
      yPosition += 30;

      if (playerScore < lowestScore) {
        lowestScore = playerScore;
        winner = player;
        tie = false; // reset tie flag since we found a new lowest score
      } else if (playerScore == lowestScore) {
        tie = true; // found another player with the same lowest score
      }
    }

    // Display the result based on whether there is a tie or a clear winner
    if (tie) {
      text("No Winner. The war starts.", (float) width / 2, yPosition + 30);
    } else if (winner != null) {
      text("Winner: " + winner.getName(), (float) width / 2, yPosition + 30);
    }
  }

  /**
   * PROVIDED: Sets the current game status message and updates the message log. If the message log
   * exceeds a maximum number of messages, the oldest message is removed.
   *
   * @param message the message to set as the current game status.
   */
  private void setGameStatus(String message) {
    gameMessages.add(message);
    int MAX_MESSAGES = 15;
    if (gameMessages.size() > MAX_MESSAGES) {
      gameMessages.remove(0); // Remove the oldest message
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // The 2 methods below this line are PROVIDED in their entirety to run the AIPlayer interactions
  // with the CABO game. Uncomment them once you are ready to add AIPlayer actions to your game!
  /////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Performs the AI player's turn by drawing a card and deciding whether to swap, discard, or use
   * an action card. If the AI player draws a card that is better than their highest card, they swap
   * it; otherwise, they discard it. If the drawn card is an action card, the AI player performs the
   * corresponding action. If the AI player's hand value is low enough, they may declare CABO.
   */

  private void performAITurn() {
    AIPlayer aiPlayer = (AIPlayer) players[currentPlayer];
    String gameStatus = aiPlayer.getName() + " is taking their turn.";
    setGameStatus(gameStatus);

    // Draw a card from the deck
    drawnCard = deck.drawCard();
    if (drawnCard == null) {
      gameOver = true;
      return;
    }

    gameStatus = aiPlayer.getName() + " drew a card.";
    setGameStatus(gameStatus);

    // Determine if AI should swap or discard
    int drawnCardValue = drawnCard.getRank();
    int highestCardIndex = aiPlayer.getHighestIndex();
    if (highestCardIndex == -1) {
      highestCardIndex = 0;
    }
    int highestCardValue = aiPlayer.getHand().getRankAtIndex(highestCardIndex);

    // Swap if the drawn card has a lower value than the highest card in hand
    if (drawnCardValue < highestCardValue) {
      BaseCard cardInHand = aiPlayer.getHand().swap(drawnCard, highestCardIndex);
      aiPlayer.setCardKnowledge(aiPlayer.getLabel(), highestCardIndex, true);
      discard.addCard(cardInHand);
      gameStatus = aiPlayer.getName() + " swapped the drawn card with card "
          + (highestCardIndex + 1) + " in their hand.";
      setGameStatus(gameStatus);
    } else if (drawnCard instanceof ActionCard) {
      // Use the action card
      String actionType = ((ActionCard) drawnCard).getActionType();
      gameStatus = aiPlayer.getName() + " uses an action card: " + actionType;
      setGameStatus(gameStatus);
      performAIAction(aiPlayer, actionType);
      discard.addCard(drawnCard);
    } else {
      // Discard the drawn card
      discard.addCard(drawnCard);
      gameStatus = aiPlayer.getName() + " discarded the drawn card: " + drawnCard;
      setGameStatus(gameStatus);
    }

    // AI may declare Cabo if hand value is low enough
    int handValue = aiPlayer.calcHandBlind();
    if (handValue <= random(13, 21) && caboPlayer == -1) {
      declareCabo();
    }

    // Prepare for the next turn
    drawnCard = null;
    nextTurn();
  }

  /**
   * Performs the specified action for the AI player based on the drawn action card. Actions include
   * peeking at their own cards, spying on another player's card, or switching cards with another
   * player.
   *
   * @param aiPlayer   the AI player performing the action.
   * @param actionType the type of action to perform ("peek", "spy", or "switch").
   */

  private void performAIAction(AIPlayer aiPlayer, String actionType) {
    Player otherPlayer = players[0]; // Assuming Player 1 is the human player
    String gameStatus = "";

    switch (actionType) {
      case "peek" -> {
        // AI peeks at one of its own cards
        int unknownCardIndex = aiPlayer.getUnknownCardIndex();
        if (unknownCardIndex != -1) {
          aiPlayer.setCardKnowledge(aiPlayer.getLabel(), unknownCardIndex, true);
          gameStatus = aiPlayer.getName() + " peeked at their card " + (unknownCardIndex + 1);
          setGameStatus(gameStatus);
        }
      }
      case "spy" -> {
        // AI spies on one of the human player's cards
        int spyIndex = aiPlayer.getSpyIndex();
        if (spyIndex != -1) {
          aiPlayer.setCardKnowledge(0, spyIndex, true);
          gameStatus = aiPlayer.getName() + " spied on Player 1's card " + (spyIndex + 1);
          setGameStatus(gameStatus);
        }
      }
      case "switch" -> {
        // AI switches one of its cards with one of the human player's cards
        int aiCardIndex = aiPlayer.getHighestIndex();
        if (aiCardIndex == -1) {
          aiCardIndex = (int) random(aiPlayer.getHand().size());
        }
        int otherCardIndex = aiPlayer.getLowestIndex(otherPlayer);
        if (otherCardIndex == -1) {
          otherCardIndex = (int) random(otherPlayer.getHand().size());
        }

        // Swap the cards between AI and the human player
        aiPlayer.getHand().switchCards(aiCardIndex, otherPlayer.getHand(), otherCardIndex);
        boolean preCardKnowledge = aiPlayer.getCardKnowledge(aiPlayer.getLabel(), aiCardIndex);
        aiPlayer.setCardKnowledge(aiPlayer.getLabel(), aiCardIndex,
            aiPlayer.getCardKnowledge(0, otherCardIndex));
        aiPlayer.setCardKnowledge(0, otherCardIndex, preCardKnowledge);

        gameStatus = aiPlayer.getName() + " switched card " + (aiCardIndex + 1) + " with "
            + otherPlayer.getName() + "'s " + (otherCardIndex + 1) + ".";
        setGameStatus(gameStatus);
      }
    }

  }
}


