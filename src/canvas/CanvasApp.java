package canvas;

/**
 * Runs the canvas app. 
 * Creates a model for the canvas, two views of the same
 * canvas, and a logger for the canvas.
 * 
 * @author sc2936@nyu.edu
 *
 */
public class CanvasApp {
  private void go() {
    CanvasModel model = new CanvasModel(600, 600);
    new CanvasView(model);
    new CanvasView(model);

    model.start();
  }

  public static void main(String[] args) {
    new CanvasApp().go();
  }
}
