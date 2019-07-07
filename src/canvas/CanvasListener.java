package canvas;

/**
 * Interface for CanvasListeners to observe CanvasModels. CanvasListeners will
 * be informed of drawing progress in a CanvasModel canvas.
 * 
 * @author sc2936@nyu.edu
 *
 */
public interface CanvasListener {

  /**
   * fired when a canvas is created
   */
  void canvasReady();

  /**
   * fired when a line segment should be drawn and made visible
   * 
   * @param startX
   * @param startY
   * @param endX
   * @param endY
   */
  void lineDrawn(int startX, int startY, int endX, int endY);

  /**
   * fired when a canvas should be cleared, all previous line drawings are no
   * longer visible.
   */
  void clearCanvas();

}
