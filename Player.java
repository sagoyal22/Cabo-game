//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Player class for backend Cabo Game
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

/**
 * This class models a CABO player for use in the CS300 P05 CABO project. A Player can be either a
 * human or a computer player; to use a fully-featured AI player, see the AIPlayer derived class.
 */
public class Player extends Object {
  private Hand hand;
  private boolean isComputer;
  private int label;
  private String name;

  /**
   * Constructs a new Player object with the given values and initializes the hand
   * 
   * @param name       - the new player's identifier
   * @param label      - the new player's label, assumed to be 0-3
   * @param isComputer - true if this is a computer player, false if this is a human
   */
  public Player(String name, int label, boolean isComputer) {
    hand = new Hand();
    this.name = name;
    this.label = label;
    this.isComputer = isComputer;
  }

  /**
   * Accesses the name of this Player
   * 
   * @return this player's identifier
   */
  public String getName() {
    return name;
  }

  /**
   * Accesses the label (0-3) of this Player
   * 
   * @return this player's label
   */
  public int getLabel() {
    return label;
  }

  /**
   * Accesses a shallow-copy reference of this player's hand
   * 
   * @return a reference to this player's hand
   */
  public Hand getHand() {
    return hand;
  }

  /**
   * Reports whether this is a computer player
   * 
   * @return true if this is a computer player, false if this is a human
   */
  public boolean isComputer() {
    return isComputer;
  }

  /**
   * Adds a card to this player's hand
   * 
   * @param card - the card to add to this player's hand
   */
  public void addCardToHand(BaseCard card) {
    hand.addCard(card);
  }
}
