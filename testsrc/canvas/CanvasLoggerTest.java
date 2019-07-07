package canvas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CanvasLoggerTest {
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
  }

  @Test
  public void getLast() {
    logger.lineDrawn(1, 1, 2, 2);
    CanvasLine line = logger.getLastLine();
    assertEquals(1, line.getStartX());
    assertEquals(1, line.getStartY());
    assertEquals(2, line.getEndX());
    assertEquals(2, line.getEndY());
  }

  @Test
  public void getSecondLast() {
    logger.lineDrawn(1, 1, 3, 3);
    logger.lineDrawn(3, 3, 2, 2);
    CanvasLine line = logger.getSecondLastLine();
    assertEquals(1, line.getStartX());
    assertEquals(1, line.getStartY());
    assertEquals(3, line.getEndX());
    assertEquals(3, line.getEndY());
  }

  @Test
  public void clear() {
    logger.lineDrawn(1, 1, 3, 3);
    logger.lineDrawn(3, 3, 2, 2);
    logger.clearCanvas();
    assertTrue(logger.emptyCanvas());
  }

  @Test
  public void startCanvas() {
    logger.canvasReady();
    CanvasLine line = logger.getLastLine();
    assertEquals(0, line.getStartX());
    assertEquals(0, line.getStartY());
    assertEquals(0, line.getEndX());
    assertEquals(0, line.getEndY());
  }

  @Test(expected = NullPointerException.class)
  public void nullModel() {
    CanvasLogger logger2 = new CanvasLogger(null);
  }

}
