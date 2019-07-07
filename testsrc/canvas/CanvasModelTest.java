package canvas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CanvasModelTest {

  private CanvasModel model;
  private CanvasLogger logger;
  private int maxY;
  private int maxX;

  @Before
  public void setUp() {
    maxX = 600;
    maxY = 600;
    model = new CanvasModel(maxX, maxY);
    logger = new CanvasLogger(model);
    model.start();
  }

  @Test
  public void addOneLinet() {
    model.setStartPositions(1, 1);
    model.setEndPositions(2, 2);
    CanvasLine line = logger.getLastLine();
    assertEquals(1, line.getStartX());
    assertEquals(1, line.getStartY());
    assertEquals(2, line.getEndX());
    assertEquals(2, line.getEndY());
  }

  // After the end position has been added, the start position of the line
  // continuation should be updated to previous end position.
  @Test
  public void dragLine() {
    model.setStartPositions(1, 1);
    model.setEndPositions(2, 2);
    model.setEndPositions(4, 3);
    model.setEndPositions(5, 6);
    CanvasLine line = logger.getLastLine();
    assertEquals(4, line.getStartX());
    assertEquals(3, line.getStartY());
  }

  @Test
  public void twoLines() {
    model.setStartPositions(1, 1);
    model.setEndPositions(2, 2);
    model.setEndPositions(5, 6);
    model.setStartPositions(3, 3);
    model.setEndPositions(1, 100);
    CanvasLine lineLast = logger.getLastLine();
    CanvasLine secondLast = logger.getSecondLastLine();
    assertEquals(3, lineLast.getStartX());
    assertEquals(3, lineLast.getStartY());

    assertEquals(5, secondLast.getEndX());
    assertEquals(6, secondLast.getEndY());
  }

  // the line should not update when the point goes out of bounds
  @Test
  public void pointOutOfBounds() {
    model.setStartPositions(1, 1);
    model.setEndPositions(400, 400);
    model.setEndPositions(-5, 5);
    CanvasLine line = logger.getLastLine();
    assertEquals(1, line.getStartX());
    assertEquals(1, line.getStartY());
    assertEquals(400, line.getEndX());
    assertEquals(400, line.getEndY());
  }

  // If the first point is out of bounds, nothing will happen since you won't be
  // able to click on the screen (you'll click into another window)
  @Test
  public void contLineInBounds() {
    model.setStartPositions(1, 1);
    int largeY = maxY + 1;
    model.setEndPositions(2, largeY);
    model.setEndPositions(500, 600);
    CanvasLine line = logger.getLastLine();
    assertEquals(1, line.getStartX());
    assertEquals(1, line.getStartY());
    assertEquals(500, line.getEndX());
    assertEquals(600, line.getEndY());
  }

  @Test
  public void clearDrawing() {
    model.setStartPositions(1, 1);
    model.setEndPositions(2, 2);
    model.setEndPositions(5, 6);
    model.setStartPositions(300, 500);
    model.setEndPositions(200, 250);
    model.clearCanvas();
    assertTrue(logger.emptyCanvas());
  }

  // Creates and adds 3 listeners, a logger and 2 views,
  // then tries to add one of those registered listeners again
  @Test(expected = IllegalArgumentException.class)
  public void addListener() {
    CanvasView view1 = new CanvasView(model);
    CanvasView view2 = new CanvasView(model);
    assertEquals(model.getNumberOfListeners(), 3);
    model.registerListener(view2);
  }

  @Test(expected = NullPointerException.class)
  public void addNullListener() {
    model.registerListener(null);
  }
  
  // removes a registered listener, and then tries to remove that same
  // listener which is no longer registered
  @Test(expected = IllegalArgumentException.class)
  public void removeListener() {
    CanvasView view1 = new CanvasView(model);
    CanvasView view2 = new CanvasView(model);
    model.deregisterListener(view2);
    model.deregisterListener(logger);
    assertEquals(model.getNumberOfListeners(), 1);
    model.deregisterListener(view2);
  }

  @Test(expected = NullPointerException.class)
  public void removeFromEmptyListenerList() {
    CanvasView view1 = new CanvasView(model);
    model.deregisterListener(view1);
    model.deregisterListener(logger);
    assertEquals(model.getNumberOfListeners(), 0);
    model.deregisterListener(view1);
  }

  @Test(expected = NullPointerException.class)
  public void removeNullListenerList() {
    model.deregisterListener(null);
  }

  @Test
  public void testToString() {
    StringBuilder sbCanvas = new StringBuilder(40);
    sbCanvas.append("Canvas Length: ");
    sbCanvas.append(maxX);
    sbCanvas.append(", Canvas Height: ");
    sbCanvas.append(maxY);
    String canvasString = sbCanvas.toString();
    assertEquals(model.toString(), canvasString);
  }

}
