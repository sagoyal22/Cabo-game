
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
