//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Base Card class for Cabo Game
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

import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * The BaseCard class represents a basic playing card with a rank, suit, and graphical
 * representation. It provides methods to draw the card, check if the mouse is over it, and flip its
 * face up or down.
 */

public class BaseCard {
  // fields
  private static PImage cardBack;
  private PImage cardImage;
  protected boolean faceUp;
  protected static PApplet processing;
  protected int rank;
  protected String suit;
  private final int WIDTH = 50;
  private final int HEIGHT = 70;
  private int x;
  private int y;

  /**
   * Constructs a new BaseCard with the specified rank and suit.
   * 
   * @param rank the rank of the card (1 to 13)
   * @param suit the suit of the card (e.g., "Hearts", "Diamonds", "Clubs", "Spades")
   * @throws IllegalStateException if the processing environment has not been set using
   *                               {@link #setProcessing(PApplet)}
   */
  public BaseCard(int rank, String suit) {
    this.rank = rank;
    this.suit = suit;
    this.faceUp = false;

    // is this the condtion we are supposed to throw a error at
    if (processing == null) {
      throw new IllegalStateException("Processing environment has not been set.");
    }
    // Load the card image (front) based on the rank and suit
    this.cardImage = processing
        .loadImage("images" + File.separator + rank + "_of_" + suit.toLowerCase() + ".png");

    // load card back image
    if (cardBack == null) {
      cardBack = processing.loadImage("images" + File.separator + "back.png");
    }
  }

  /**
   * Sets the Processing PApplet environment for this class. This must be called before creating any
   * instances of BaseCard.
   * 
   * @param processing the PApplet instance to set as the processing environment
   */

  public static void setProcessing(processing.core.PApplet processing) {
    BaseCard.processing = processing;
  }

  /**
   * Gets the rank of this card. If the card is the King of Diamonds, it returns -1.
   * 
   * @return the rank of this card, or -1 if it is the King of Diamonds
   */

  public int getRank() {
    if (rank == 13 && "Diamonds".equals(suit)) {
      return -1;
    }
    return rank;
  }

  /**
   * Sets whether this card is face-up or face-down.
   * 
   * @param faceUp true to set the card face-up, false to set it face-down
   */

  public void setFaceUp(boolean faceUp) {
    this.faceUp = faceUp;
  }

  /**
   * Returns a string representation of this card.
   * 
   * @return the string representation of this card, including its suit and rank
   */
  @Override
  public String toString() {
    return suit + " " + rank;
  }

  /**
   * Draws the card at the specified x and y position. If the card is face-up, it draws the front
   * image; otherwise, it draws the back image.
   * 
   * @param xPosition the x-coordinate where the card should be drawn
   * @param yPosition the y-coordinate where the card should be drawn
   */

  public void draw(int xPosition, int yPosition) {
    // draw white rectangle
    this.x = xPosition;
    this.y = yPosition;
    processing.fill(255); // Set the fill color to white
    processing.rect(xPosition, yPosition, WIDTH, HEIGHT); // Draw the rectangle

    // Draw the card image based on whether it's face-up or face-down
    if (faceUp) {
      processing.image(cardImage, xPosition, yPosition, WIDTH, HEIGHT);
    } else {
      processing.image(cardBack, xPosition, yPosition, WIDTH, HEIGHT);
    }
  }

  /**
   * Checks whether the mouse is currently hovering over this card.
   * 
   * @return true if the mouse is over this card, false otherwise
   */
  public boolean isMouseOver() {
    // Mouse pointer location x, y
    int mousePositionX = processing.mouseX;
    int mousePositionY = processing.mouseY;

    // Define the ranges for checking if the mouse is over the card
    float range1 = x; // left edge of the card
    float range2 = x + WIDTH; // right edge of the card
    float range3 = y; // top edge of the card
    float range4 = y + HEIGHT; // bottom edge of the card

    // Check if the mouse is within the card's bounds
    return (mousePositionX > range1 && mousePositionX < range2 && mousePositionY > range3
        && mousePositionY < range4);
  }
}


