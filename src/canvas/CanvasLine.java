package canvas;

/**
 * Creates a line object which has a start and end point. These objects are used
 * by the logger to keep track of each line segment that is drawn and is used
 * for testing.
 * 
 * @author sc2936@nyu.edu
 *
 */
class CanvasLine {
  private int startX;
  private int startY;
  private int endX;
  private int endY;

  /**
   * Constructor takes the start and end point of a line segment.
   * 
   * @param startX
   * @param startY
   * @param endX
   * @param endY
   */
  public CanvasLine(int startX, int startY, int endX, int endY) {
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
  }

  /**
   * Getter for the starting x int coordinate
   * 
   * @return startX
   */
  public int getStartX() {
    return startX;
  }

  /**
   * Getter for the starting y int coordinate
   * 
   * @return startY
   */
  public int getStartY() {
    return startY;
  }

  /**
   * Getter for the ending x int coordinate
   * 
   * @return endX
   */
  public int getEndX() {
    return endX;
  }

  /**
   * Getter for the ending y int coordinate
   * 
   * @return endY
   */
  public int getEndY() {
    return endY;
  }

}
