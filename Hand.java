//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Hand class for Cabo Game
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

/**
 * This class models a mini-Deck that each player holds - their "hand" of cards.
 */
public class Hand extends Deck {
  private final int HAND_SIZE = 4;

  /**
   * Create a new empty deck for this hand
   */
  public Hand() {
    super(new ArrayList<>());
  }

  /**
   * Overrides Deck's addCard() method to prevent this player being dealt more than HAND_SIZE cards
   * 
   * @param card - the card to add to this hand
   */
  @Override
  public void addCard(BaseCard card) {
    if (cardList.size() < HAND_SIZE) {
      cardList.add(card);
    } else {
      throw new IllegalStateException("extra cards!!");
    }
  }

  /**
   * Replaces the card at the given index (assumed to be between 0 and (HAND_SIZE-1)) with the
   * provided card, and returns the card that was previously at that index.
   * 
   * @param newCard - the card to swap into this hand
   * @param index   - the index to place the new card at
   * @return the card that was previously at that index
   */
  public BaseCard swap(BaseCard newCard, int index) {
    BaseCard temp = cardList.set(index, newCard);
    return temp;
  }

  /**
   * Switches a card in this hand with a card in the other hand.
   * 
   * @param myIndex    - the index of the card in this hand to switch
   * @param otherHand  - the other hand to switch cards with
   * @param otherIndex - the index of the card in the other hand to switch
   */
  public void switchCards(int myIndex, Hand otherHand, int otherIndex) {
    BaseCard otherCard = otherHand.cardList.get(otherIndex);
    otherHand.cardList.set(otherIndex, this.swap(otherCard, myIndex));
  }

  /**
   * Changes the face-up value of the card at the given index to the provided value
   * 
   * @param index  - the index of the card to change
   * @param faceUp - true if this card should be face-up, false if it should be face-down
   */
  public void setFaceUp(int index, boolean faceUp) {
    cardList.get(index).setFaceUp(faceUp);
  }

  /**
   * Draws the entire hand at the given y-coordinate. To calculate the x-coordinate of each card,
   * use (50 + 60*index).
   * 
   * @param y - the y-coordinate of the upper-left corner of all cards in this hand
   */
  public void draw(int y) {
    for (int i = 0; i < cardList.size(); i++) {
      int x = 50 + 60 * i;
      cardList.get(i).draw(x, y);
    }
  }

  /**
   * Checks if the mouse is currently over any of the cards in this hand, and returns the index of
   * any card which the mouse is over, or -1 if the mouse is not currently over any card in this
   * hand.
   * 
   * @return the index of a card in this hand which the mouse is over, or -1 if the mouse is not
   *         over any cards in this hand
   */
  public int indexOfMouseOver() {
    for (int i = 0; i < cardList.size(); i++) {
      if (cardList.get(i).isMouseOver()) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Accesses the rank of a card at a given index
   * 
   * @param index - the index of the card to access
   * @return the rank of the card at that index
   */
  public int getRankAtIndex(int index) {
    return cardList.get(index).getRank();
  }

  /**
   * Determines the total value of the cards in this hand, as a sum of the ranks of each of the
   * cards
   * 
   * @return the total value of this Player's hand
   */
  public int calcHand() {
    int totalValue = 0;
    for (int i = 0; i < cardList.size(); i++) {
      totalValue += getRankAtIndex(i);
    }
    return totalValue;
  }
}
