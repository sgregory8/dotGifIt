package com.gregory.learning.Gui.recording;

import com.gregory.learning.service.GifMaker;
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BackgroundPane extends JPanel implements ActionListener, MouseMotionListener {

  private Point mouseAnchor;
  private Point dragPoint;
  private JPanel jPanel;
  private JLabel jLabel;
  int darkX;
  int darkY;
  int darkW;
  int darkH;
  int screenHeight, screenWidth;
  Point topLeftPixel, topRightPixel, bottomLeftPixel, bottomRightPixel;
  private JButton recordButton;
  private JButton backButton;
  private JButton stopButton;
  private boolean listenersActive = true;
  private boolean firstRender = true;

  private SelectionPane selectionPane;

  private GifMaker gifMaker;

  public BackgroundPane(GifMaker gifMaker) {
    addMouseMotionListener(this);
    this.gifMaker = gifMaker;
    super.setOpaque(false);
    // Get the size of the screen
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    screenHeight = (int) dim.getHeight();
    screenWidth = (int) dim.getWidth();
    topLeftPixel = new Point(0, 0);
    bottomLeftPixel = new Point(0, dim.height);
    topRightPixel = new Point(dim.width, 0);
    bottomRightPixel = new Point(dim.width, dim.height);

    // Determine the new location of the window
    int w = 0;
    int h = 0;
    int x = (dim.width - w) / 2;
    int y = (dim.height - h) / 2;

    selectionPane = new SelectionPane();
    jLabel = new JLabel("Drag to set recording area");
    jLabel.setFont(jLabel.getFont().deriveFont(20.0f));
    jLabel.setForeground(Color.RED);
    jLabel.setLocation(100, 100);
    jPanel = new JPanel();
    jPanel.setSize(500, 100);
    jPanel.setOpaque(false);
    jPanel.setLayout(new FlowLayout());
    recordButton = new JButton("Record");
    backButton = new JButton("Back");
    stopButton = new JButton("Stop");
    backButton.setOpaque(false);
    stopButton.setVisible(false);
    jPanel.add(recordButton);
    jPanel.add(backButton);
    jPanel.add(stopButton);
    backButton.addActionListener(this);
    recordButton.addActionListener(this);
    stopButton.addActionListener(this);
    setLayout(null);
    setDarkCoordinates(x, y, w, h);

    jPanel.setLocation(x + w / 2 - jPanel.getWidth() / 2, y + h);
    super.add(selectionPane);
    super.add(jLabel);

    MouseAdapter adapter = new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
//        firstRender = false;
        if (!listenersActive) {
          return;
        }
//        selectionPane.remove(jLabel);
        mouseAnchor = e.getPoint();
        dragPoint = null;
//        selectionPane.setLocation(mouseAnchor);
//        setDarkCoordinates(x, y, 0, 0);
//        jPanel.setLocation(e.getX() - jPanel.getWidth() / 2, e.getY());
//        selectionPane.setSize(0, 0);
        repaint();
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        recordButton.setEnabled(false);
        firstRender = false;
        if (!listenersActive) {
          return;
        }
        selectionPane.remove(jLabel);
        BackgroundPane.super.add(jPanel);
        dragPoint = e.getPoint();
        int width = dragPoint.x - mouseAnchor.x;
        int height = dragPoint.y - mouseAnchor.y;

        int x = mouseAnchor.x;
        int y = mouseAnchor.y;

        if (width < 0) {
          x = dragPoint.x;
          width *= -1;
        }
        if (height < 0) {
          y = dragPoint.y;
          height *= -1;
        }

        selectionPane.setBounds(x, y, width, height);
        jPanel.setLocation(x + width / 2 - jPanel.getWidth() / 2, y + height);
        selectionPane.revalidate();
        setDarkCoordinates(x, y, width, height);
        System.out.println("coords are: " + selectionPane.getX() + " " + selectionPane.getY());
        System.out.println("height and width are :" + darkH + " " + darkW);

        if (darkH > 5 || darkW > 5) {
          recordButton.setEnabled(true);
        }

        repaint();
      }

    };
    addMouseListener(adapter);
    addMouseMotionListener(adapter);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton) e.getSource();
    if (clicked == backButton) {
      JComponent comp = (JComponent) e.getSource();
      Window win = SwingUtilities.getWindowAncestor(comp);
      win.dispose();
    }
    if (clicked == recordButton) {
      backButton.setVisible(false);
      recordButton.setVisible(false);
      listenersActive = false;
      stopButton.setVisible(true);
      new Thread(() -> {
        try {
          gifMaker
              .startRecording(selectionPane.getLocationOnScreen().x + 1,
                  selectionPane.getLocationOnScreen().y + 1,
                  selectionPane.getWidth() - 2,
                  selectionPane.getHeight() - 2);
        } catch (IOException ex) {
          ex.printStackTrace();
        } catch (AWTException ex) {
          ex.printStackTrace();
        }
      }).start();
    }
    if (clicked == stopButton) {
      try {
        gifMaker.stopRecording();
      } catch (IOException ex) {
        ex.printStackTrace();
      } catch (AWTException ex) {
        ex.printStackTrace();
      }
      listenersActive = true;
      JComponent comp = (JComponent) e.getSource();
      Window win = SwingUtilities.getWindowAncestor(comp);
      win.dispose();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(new Color(0, 0, 0, 60));
    // Left
    g2d.fillRect(0, darkY, darkX, selectionPane.getHeight());
    // Right
    g2d.fillRect(darkX + selectionPane.getWidth(), darkY, screenWidth, selectionPane.getHeight());
    // Top
    g2d.fillRect(0, 0, screenWidth, darkY);
    // Bottom
    g2d.fillRect(0, darkY + selectionPane.getHeight(), screenWidth, screenHeight);

    g2d.dispose();
  }

  private void setDarkCoordinates(int darkX, int darkY, int darkW, int darkH) {
    this.darkX = darkX;
    this.darkY = darkY;
    this.darkW = darkW;
    this.darkH = darkH;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    System.out.println("cursor is: " + getCursor());
    gifMaker.setCursorInformation(getCursor(), e.getX(), e.getY());
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    System.out.println("cursor is: " + getCursor());
    gifMaker.setCursorInformation(getCursor(), e.getX(), e.getY());

  }

  public class SelectionPane extends JPanel {

    public SelectionPane() {
      setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g.create();
      g2d.setColor(new Color(0, 0, 0, 0));
      g2d.fillRect(0, 0, getWidth(), getHeight());

      float dash1[] = {5.0f};
      BasicStroke dashed =
          new BasicStroke(1.5f,
              BasicStroke.CAP_BUTT,
              BasicStroke.JOIN_MITER,
              5.0f, dash1, 0.0f);
      g2d.setColor(Color.BLACK);
      g2d.setStroke(dashed);
      g2d.drawRect(0, 0, getWidth(), getHeight());
      g2d.dispose();
    }
  }

  public static Rectangle getScreenViewableBounds() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();

    return getScreenViewableBounds(gd);
  }

  public static Rectangle getScreenViewableBounds(GraphicsDevice gd) {
    Rectangle bounds = new Rectangle(0, 0, 0, 0);
    if (gd != null) {
      GraphicsConfiguration gc = gd.getDefaultConfiguration();
      bounds = gc.getBounds();

      Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

      bounds.x += insets.left;
      bounds.y += insets.top;
      bounds.width -= (insets.left + insets.right);
      bounds.height -= (insets.top + insets.bottom);
    }
    return bounds;
  }
}
