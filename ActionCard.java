//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Action Card for Cabo Game
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
 * The ActionCard class represents a special type of card that performs an action in the game. It
 * extends the {@link BaseCard} class by adding an action type (e.g., "peek", "spy", "switch").
 */
public class ActionCard extends BaseCard {
  private String actionType;

  /**
   * Constructs a new ActionCard with the specified rank, suit, and action type.
   * 
   * @param rank       the rank of the card (1 to 13)
   * @param suit       the suit of the card (e.g., "Hearts", "Diamonds", "Clubs", "Spades")
   * @param actionType the type of action this card performs (e.g., "peek", "spy", "switch")
   */
  public ActionCard(int rank, String suit, String actionType) {
    super(rank, suit);
    this.actionType = actionType;
  }

  /**
   * Returns the type of action that this card performs.
   * 
   * @return the action type of this card (e.g., "peek", "spy", "switch")
   */
  public String getActionType() {
    return actionType;
  }

}
