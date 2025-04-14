

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
