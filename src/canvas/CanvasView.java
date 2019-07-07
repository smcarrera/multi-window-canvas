package canvas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Creates the GUI to draw lines on a canvas. CanvasView implements
 * CanvasListener and observes a CanvasModel. Multiple views that observe the
 * same model will show the same view.
 * 
 * The GUI has a CanvasPaintArea, where the actual drawing happens. 
 * The CanvasPaintArea is a panel object which is instantiated once the main frame is 
 * set up.
 * Mainframe also contains a control panel which houses control buttons. 
 * Currently the only control button is to clear the drawing. 
 * All drawings are by default in black, but the control buttons could be expanded.
 * 
 * @author sc2936@nyu.edu
 *
 */
public class CanvasView implements CanvasListener {
  private JFrame mainFrame = new JFrame();
  private CanvasPaintArea paintPanel;
  private JPanel controlPanel = new JPanel(new BorderLayout());
  private JPanel messagePanel = new JPanel(new BorderLayout());
  private JLabel statusLabel = new JLabel("Start Painting");
  private CanvasModel model;

  /**
   * Constructor registers the view as a CanvasModel listener and creates the view
   * of the canvas. the canvas is not visible until a CanvasModel is started. The
   * canvas contains: the main frame, the CanvasPaintArea, the control panel, and
   * a text area where messages can be shown.
   * 
   * @param model
   * @throws NullPointerException
   *           if the model is null
   */
  public CanvasView(CanvasModel model) {
    if (model == null) {
      throw new NullPointerException("model cannot be null");
    }
    this.model = model;
    model.registerListener(this);
    setupMainFrame();
    startMouseListeners();
    startMouseMotionListener();
  }

  private void setupMainFrame() {
    mainFrame.getContentPane().setLayout(new BorderLayout());
    mainFrame.getContentPane().add(controlPanel, BorderLayout.NORTH);
    mainFrame.setBackground(Color.WHITE);
    setupControls();

    paintPanel = new CanvasPaintArea();
    paintPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    mainFrame.getContentPane().add(paintPanel, BorderLayout.CENTER);

    mainFrame.getContentPane().add(messagePanel, BorderLayout.SOUTH);
    messagePanel.add(statusLabel, BorderLayout.CENTER);

    mainFrame.setSize(model.getCanvasLength(), model.getCanvasHeight());
    mainFrame.setResizable(false);
    setupClose();
  }

  /**
   * Setup of control panel buttons. Could be expanded beyond clear.
   */
  private void setupControls() {
    setupClear();
  }

  private void setupClear() {
    JButton clearButton = new JButton("Clear");
    controlPanel.add(clearButton, BorderLayout.CENTER);
    clearButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        model.clearCanvas();
      }
    });
  }
  
  /**
   * On closing a CanvasView, deregisters its listener 
   * and keeps the application active if other frames are open
   */
  private void setupClose() {
    CanvasView toRemove = this;
    mainFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        model.deregisterListener(toRemove);
        mainFrame.dispose();   
      }
    });
  }

  /**
   * Listens for the mouse listeners and updates the model with the starting
   * positions of each line.
   */
  public void startMouseListeners() {
    paintPanel.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        model.setStartPositions(e.getX(), e.getY());
      }
    });
  }

  /**
   * Listens for the mouse motion listeners and updates the model with the end
   * positions of each line.
   */
  public void startMouseMotionListener() {
    paintPanel.addMouseMotionListener(new MouseAdapter() {
      public void mouseDragged(MouseEvent e) {
        model.setEndPositions(e.getX(), e.getY());
      }
    });
  }

  @Override
  public void canvasReady() {
    mainFrame.setVisible(true);
  }

  @Override
  public void lineDrawn(int startX, int startY, int endX, int endY) {
    paintPanel.graphics.drawLine(startX, startY, endX, endY);
    paintPanel.repaint();
  }

  @Override
  public void clearCanvas() {
    statusLabel.setText("");
    paintPanel.clearCanvas();
  }

  /**
   * The canvas paint area object This is where the actual drawing can be
   * rendered. Uses a default constructor.
   * 
   * @author sc2936@nyu.edu
   *
   */
  private class CanvasPaintArea extends JPanel {
    Graphics2D graphics;
    Image image;
   
    /**
     * will clear the canvas
     * // could have paint panel size increased to match when window is expanded
     * // currently expansion is not made available
     */
    public void clearCanvas() {
      graphics.setPaint(Color.white);
      graphics.fillRect(0, 0, paintPanel.getSize().width, paintPanel.getSize().height);
      graphics.setPaint(Color.black);
      paintPanel.repaint();
    }

    /**
     * will render the image of the drawing
     */
    public void paintComponent(Graphics g) {
      if (image == null) {
        image = createImage(getSize().width, getSize().height);
        graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      }
      g.drawImage(image, 0, 0, null);
    }
  }

}
