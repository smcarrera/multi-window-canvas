package canvas;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains all of the logic necessary to draw lines on a canvas. Contains a
 * list of CanvasListeners which observe changes to the model. Therefore Two
 * drawers can draw on a canvas and see the same view. Multiple disjoint lines
 * or continuous line can be drawn. The position of the line is not updated if
 * the drawer goes beyond the border of the canvas.
 * 
 * @author sc2936@nyu.edu
 *
 */
public class CanvasModel {
  private List<CanvasListener> listeners;
  private int canvasLength;
  private int canvasHeight;
  private int startX;
  private int endX;
  private int startY;
  private int endY;

  /**
   * Constructor initializes the size of the canvas and set the default starting
   * point.
   * 
   * @param length
   * @param height
   */
  public CanvasModel(int length, int height) {
    listeners = new LinkedList<CanvasListener>();
    canvasLength = length;
    canvasHeight = height;
    defaultPosition();
  }

  private void defaultPosition() {
    startX = 0;
    endX = 0;
    startY = 0;
    endY = 0;
  }

  /**
   * Lets listeners know that a canvas has started.
   */
  public void start() {
    fireCanvasReadyEvent();
  }

  /**
   * If the mouse goes off the screen ie out of bounds, then the start position is
   * not updated until the mouse is within bounds.
   * 
   * @param x
   * @param y
   */
  public void setStartPositions(int x, int y) {
    if (checkBounds(x, y)) {
      startX = x;
      startY = y;
    }
  }

  /**
   * If the mouse goes off the screen ie out of bounds, then the end position is
   * not updated until the mouse is within bounds. If the next point is within
   * bounds, after the listeners are informed to draw the line segment, the
   * following start point is set to the previous end point for line continuation.
   * 
   * @param x
   * @param y
   */
  public void setEndPositions(int x, int y) {
    if (checkBounds(x, y)) {
      endX = x;
      endY = y;
      fireLineDrawnEvent(startX, startY, endX, endY);
      startX = endX;
      startY = endY;
    }
  }

  /**
   * Lets all listeners know to clear the canvas of previous line drawings.
   */
  public void clearCanvas() {
    defaultPosition();
    fireClearCanvasEvent();
  }

  /**
   * returns the int length of the canvas set by the constructor.
   * 
   * @return canvasLength
   */
  public int getCanvasLength() {
    return canvasLength;
  }

  /**
   * returns the int height of the canvas set by the constructor.
   * 
   * @return canvasHeight
   */
  public int getCanvasHeight() {
    return canvasHeight;
  }

  private boolean checkBounds(int x, int y) {
    if (x < 0 || x > canvasLength) {
      return false;
    }
    if (y < 0 || y > canvasHeight) {
      return false;
    }
    return true;
  }

  private void fireCanvasReadyEvent() {
    for (CanvasListener listener : listeners) {
      listener.canvasReady();
    }
  }

  private void fireLineDrawnEvent(int startX, int startY, int endX, int endY) {
    for (CanvasListener listener : listeners) {
      listener.lineDrawn(startX, startY, endX, endY);
    }
  }

  private void fireClearCanvasEvent() {
    for (CanvasListener listener : listeners) {
      listener.clearCanvas();
    }
  }

  /**
   * Adds a CanvasListener to the list of listeners.
   * 
   * @param listener
   * @throws NullPointerException if a null is passed in
   * @throws IllegalArgumentException if an object which has registered as a listener 
   * tries to register again.
   */
  public void registerListener(CanvasListener listener) {
    if (listener == null) {
      throw new NullPointerException("listener cannot be null");
    }
    if (listeners.contains(listener)) {
      throw new IllegalArgumentException("Listener is already registered");
    }
    listeners.add(listener);
  }

  /**
   * Deregisters a Listener
   * 
   * @param listener
   * @throws NullPointerException if a model tries to deregister from an empty list.
   * @throws NullPointerException if a null is passed in.
   * @throws IllegalArgumentException if an object which has not registered as a listener 
   * tries to unregister.
   * 
   */
  public void deregisterListener(CanvasListener listener) {
    if (listeners.size() == 0) {
      throw new NullPointerException("no listeners");
    }
    if (listener == null) {
      throw new NullPointerException("listener cannot be null");
    }
    if (!listeners.contains(listener)) {
      throw new IllegalArgumentException("listenr not registered");
    }
    listeners.remove(listener);
  }

  @Override
  public String toString() {
    StringBuilder sbCanvas = new StringBuilder(40);
    sbCanvas.append("Canvas Length: ");
    sbCanvas.append(canvasLength);
    sbCanvas.append(", Canvas Height: ");
    sbCanvas.append(canvasHeight);
    return sbCanvas.toString();
  }

  /**
   * For testing only
   * @return number of listeners that are registered
   */
  public int getNumberOfListeners() {
    return listeners.size();
  }

}
