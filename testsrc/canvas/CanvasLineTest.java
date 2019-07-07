package canvas;

import static org.junit.Assert.*;

import org.junit.Test;

public class CanvasLineTest {

  @Test
  public void createLine() {
    CanvasLine line = new CanvasLine(1, 2, 3, 4);
    assertEquals(1, line.getStartX());
    assertEquals(2, line.getStartY());
    assertEquals(3, line.getEndX());
    assertEquals(4, line.getEndY());
  }

}
