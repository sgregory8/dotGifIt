package com.gregory.learning.Gui.recording;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ResizableRectangle extends JPanel {

  private static final JFrame transparentFrame = new JFrame();
  private JPanel selectableFrame;

  int x, y, x2, y2;

  public void initResizableRectangle() {
    transparentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ResizableRectangle m = new ResizableRectangle();
    transparentFrame.setUndecorated(true);
    transparentFrame.setBackground(new Color(0,0,0,30));
    m.setOpaque(false);
    transparentFrame.setContentPane(m);
    transparentFrame.setAlwaysOnTop(true);
    transparentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    transparentFrame.setVisible(true);
  }

  public ResizableRectangle() {
    x = y = x2 = y2 = 0; //
    super.setOpaque(false);
    MyMouseListener listener = new MyMouseListener();
    addMouseListener(listener);
    addMouseMotionListener(listener);
  }

  public void setStartPoint(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setEndPoint(int x, int y) {
    x2 = (x);
    y2 = (y);
  }

  public void drawRectAndPanel(Graphics g, int x, int y, int x2, int y2) {
    int px = Math.min(x, x2);
    int py = Math.min(y, y2);
    int pw = Math.abs(x - x2);
    int ph = Math.abs(y - y2);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(new Color(128, 128, 128, 64));
    g2d.fillRect(px, py, pw, ph);
    float dashWidth[] = {5.0f};
    BasicStroke dashed =
        new BasicStroke(1.5f,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            5.0f, dashWidth, 0.0f);
    g2d.setColor(Color.MAGENTA);
    g2d.setStroke(dashed);
    g2d.drawRect(px, py, pw, ph);
    if (selectableFrame == null) {
      selectableFrame = new JPanel();
      selectableFrame.setOpaque(false);
      selectableFrame.setLayout(new FlowLayout());
      selectableFrame.add(new JButton("Record"));
      selectableFrame.add(new JButton("Back"));
      transparentFrame.add(selectableFrame);
    }
    selectableFrame.setLocation(px + pw/2 - (selectableFrame.getWidth()/2), py + ph);
  }

  class MyMouseListener extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
      setStartPoint(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      setEndPoint(e.getX(), e.getY());
      repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      setEndPoint(e.getX(), e.getY());
      repaint();
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.MAGENTA);
    drawRectAndPanel(g, x, y, x2, y2);
  }



}
