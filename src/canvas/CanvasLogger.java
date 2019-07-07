package canvas;

import java.util.Stack;

/**
 * Implements the CanvasListener Interface. Logs the line drawings made by the
 * CanvasModel. Saves line segments drawn as CanvasLine objects in a stack. The
 * last segments drawn can be used for testing. Multiple loggers can log the
 * same CanvasModel.
 * 
 * @author sc2936@nyu.edu
 *
 */
class CanvasLogger implements CanvasListener {
  private Stack<CanvasLine> lineStack = new Stack<CanvasLine>();

  /**
   * Registers the CanvasLogger as a listener of a non null CanvasModel
   * 
   * @param model
   * @throws NullPointerException if the model is null
   */
  public CanvasLogger(CanvasModel model) {
    if (model == null) {
      throw new NullPointerException("model cannot be null");
    }
    model.registerListener(this);
  }

  /**
   * Returns the last line segment which was drawn
   * 
   * @return CanvasLine object
   */
  public CanvasLine getLastLine() {
    return lineStack.peek();
  }

  /**
   * Returns the second to last line segment which was drawn.
   * 
   * @return CanvasLine object
   */
  public CanvasLine getSecondLastLine() {
    CanvasLine line1 = lineStack.pop();
    CanvasLine line2 = lineStack.peek();
    lineStack.push(line1);
    return line2;
  }

  /**
   * Used to test if the clear function correctly works.
   * 
   * @return true if the lineStack of CanvasLine objects is empty, otherwise false.
   */
  public boolean emptyCanvas() {
    return lineStack.isEmpty();
  }

  /**
   * Should make a canvas visible and perform setup as a result of start. The
   * default position is added to the lineStack logger.
   */
  @Override
  public void canvasReady() {
    CanvasLine line = new CanvasLine(0, 0, 0, 0);
    lineStack.push(line);
  }

  /**
   * draws line segment between two end points. logs the line segment.
   */
  @Override
  public void lineDrawn(int startX, int startY, int endX, int endY) {
    CanvasLine line = new CanvasLine(startX, startY, endX, endY);
    lineStack.push(line);
  }

  /**
   * clears the canvas of so no previous drawing line segments are visible. Also
   * clears the logged stack of previously drawn segments.
   */
  @Override
  public void clearCanvas() {
    lineStack.clear();
  }

}
