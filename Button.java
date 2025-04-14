

import processing.core.PApplet;

/**
 * The Button class represents a simple interactive button in the Processing environment. It
 * displays a label and can change its appearance when active or inactive. The button's appearance
 * and behavior are managed through the Processing library.
 */
public class Button {
  private boolean active;
  private int height;
  private String label;
  protected static processing.core.PApplet processing;
  private int width;
  private int x;
  private int y;

  /**
   * Constructs a Button with the specified label and position, which is inactive by default. Throws
   * an IllegalStateException if the Processing environment has not been initialized.
   * 
   * @param label  - the text label displayed on the button.
   * @param x      - the x-coordinate of the top-left corner of the button.
   * @param y      - the y-coordinate of the top-left corner of the button.
   * @param width  - the width of the button.
   * @param height - the height of the button.
   */
  public Button(String label, int x, int y, int width, int height) {
    if (processing == null) {
      throw new IllegalStateException("Processing Environment not initialized");
    }
    this.label = label;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    active = false;
  }

  /**
   * Sets the Processing environment to be used by the Button class. This must be called before
   * creating any buttons.
   * 
   * @param - the Processing environment to be used for drawing and interaction.
   */
  public static void setProcessing(processing.core.PApplet processing) {
    Button.processing = processing; // COPIED FROM DECK CLASS

  }

  /**
   * Returns the label of this button
   * 
   * @return this button's current label
   */
  public String getLabel() {
    return label;
  }

  /**
   * Changes the label of this button
   * 
   * @param - new label for this button
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * Returns whether the button is currently active
   * 
   * @return true if the button is active, false otherwise.
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets the active state of the button. If true, the button will be rendered as active. If false,
   * it will be rendered as inactive.
   * 
   * @param active - the new active state of the button.
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Renders the button on the Processing canvas. The button changes color based on its isActive
   * parameter and whether the mouse is currently over it
   */
  public void draw() {
    if (active && isMouseOver()) {
      processing.fill(150);
    } else if (!active) {
      processing.fill(255, 51, 51);
    } else {
      processing.fill(200);
    }

    processing.rect(x, y, width, height, 5);
    processing.fill(0);
    processing.textSize(14);
    processing.textAlign(PApplet.CENTER, PApplet.CENTER);
    processing.text(label, x + width / 2, y + height / 2);

  }

  public boolean isMouseOver() {
    // Mouse pointer location x, y
    int mousePositionX = processing.mouseX;
    int mousePositionY = processing.mouseY;

    // Define the ranges for checking if the mouse is over the card
    float range1 = x; // left edge of the card
    float range2 = x + width; // right edge of the card
    float range3 = y; // top edge of the card
    float range4 = y + height; // bottom edge of the card

    // Check if the mouse is within the card's bounds
    return (mousePositionX > range1 && mousePositionX < range2 && mousePositionY > range3
        && mousePositionY < range4);
  }



}
