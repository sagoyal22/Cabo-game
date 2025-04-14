//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Deck class for Cabo Game
// Course: CS 300 Fall 2024
//
// Author: George Sukhotin
// Email: sukhotin@wisc.edu
// Lecturer: (Blerina Gkotse)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Saniya Goyal
// Partner Email: sgoyal@wisc.edu
// Partner Lecturer's Name: (Blerina Gkotse)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// _X__ Write-up states that pair programming is allowed for this assignment.
// _X__ We have both read and understand the course Pair Programming Policy.
// _X__ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons: None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////


import java.util.ArrayList;
import java.util.Collections;
import processing.core.PApplet;

/**
 * The Deck class represents a deck of playing cards for the game Cabo. It manages a collection of
 * cards, including shuffling, drawing, and adding cards.
 */
public class Deck {
  // fields
  protected ArrayList<BaseCard> cardList;
  protected static PApplet processing;

  /**
   * Constructs a new Deck with the specified list of cards.
   * 
   * @param deck the list of cards to initialize this deck with
   * @throws IllegalStateException if the Processing environment has not been set
   */
  public Deck(ArrayList<BaseCard> deck) {
    if (processing == null) {
      throw new IllegalStateException("Processing environment has not been set.");
    }
    this.cardList = deck; // Initialize cardList with the provided deck
  }

  /**
   * Sets the Processing environment for the Deck class.
   * 
   * @param processing the PApplet object representing the Processing environment
   */
  public static void setProcessing(processing.core.PApplet processing) {
    Deck.processing = processing;
  }

  /**
   * Checks if the deck is empty.
   * 
   * @return true if the deck has no cards, false otherwise
   */
  public boolean isEmpty() {
    return cardList.isEmpty(); // Return true if cardList has no cards, false otherwise
  }

  /**
   * Draws and removes the top card from the deck.
   * 
   * @return the top card from the deck, or null if the deck is empty
   */
  public BaseCard drawCard() {
    if (isEmpty()) {
      return null; // Return null if the deck is empty
    }
    return cardList.remove(cardList.size() - 1); // Remove and return the top card
  }

  /**
   * Adds a card to the deck.
   * 
   * @param card the card to be added to the deck
   */
  public void addCard(BaseCard card) {
    cardList.add(card);
  }

  /**
   * Returns the number of cards remaining in the deck.
   * 
   * @return the size of the deck
   */

  public int size() {
    return cardList.size();
  }

  /**
   * Draws the deck or discard pile at the specified position. Displays the deck as a stack of cards
   * face-down, or displays the top card face-up for a discard pile.
   * 
   * @param x         the x-coordinate of the deck on the screen
   * @param y         the y-coordinate of the deck on the screen
   * @param isDiscard true if this is a discard pile, false if it is the draw pile
   */
  public void draw(int x, int y, boolean isDiscard) {

    if (isEmpty()) {
      processing.stroke(0);
      processing.fill(0);
      processing.rect(x, y, 50, 70, 7);
      processing.fill(255);
      processing.textSize(12);
      processing.textAlign(processing.CENTER, processing.CENTER);
      processing.text("Empty", x + 25, y + 35);
    } else {
      // Draw the top card from the deck
      BaseCard topCard = cardList.get(cardList.size() - 1); // Draw the top card from the deck

      if (isDiscard) {
        // If this is a discard pile, draw the card face-up
        topCard.setFaceUp(true);
      } else {
        // If this is the draw pile, draw the card face-down
        topCard.setFaceUp(false);
      }
      topCard.draw(x, y);
    }
  }

  /**
   * Sets up the deck with CABO cards, including action cards. Initializes the deck with all
   * necessary cards and shuffles them.
   *
   * @return the completed ArrayList of CABO cards
   */
  public static ArrayList<BaseCard> createDeck() {
    ArrayList<BaseCard> cardList = new ArrayList<>();

    // Define the suits
    String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};

    // Cards from 1 (Ace) to 13 (King)
    for (int rank = 1; rank <= 13; ++rank) {
      // Loop through each suit
      for (String suit : suits) {
        if (rank >= 7 && rank <= 12) {
          // Special action cards
          String actionType = "";
          if (rank == 7 || rank == 8) {
            actionType = "peek";
          } else if (rank == 9 || rank == 10) {
            actionType = "spy";
          } else {
            actionType = "switch";
          }
          cardList.add(new ActionCard(rank, suit, actionType)); // Add ActionCard to deck
        } else {
          cardList.add(new BaseCard(rank, suit)); // Add NumberCard to deck
        }
      }
    }
    Collections.shuffle(cardList);
    return cardList;
  }

}
