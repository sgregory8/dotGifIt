package com.gregory.learning.Gui.panels;

import com.gregory.learning.service.InitService;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DemoPanel extends JPanel implements ActionListener {

  private static final String GIF_DIRECTORY = "images";
  private static final String ICON_NAME = "Demo";
  private static final String ICON_TYPE = ".gif";

  private JLabel jLabel;
  private JButton jButton;
  private JCheckBox jCheckBox;
  private JPanel jPanel;

  private ImageIcon imageIcon;

  public DemoPanel() throws URISyntaxException {
    this.imageIcon = new ImageIcon(getClass().getClassLoader()
        .getResource(GIF_DIRECTORY + File.separator + ICON_NAME + ICON_TYPE));
    Image image = imageIcon.getImage();// transform it
    imageIcon = new ImageIcon(image);

    jButton = new JButton("Got it");

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    int screenHeight = (int) dim.getHeight();
    int screenWidth = (int) dim.getWidth();

    setLayout(null);

    jLabel = new JLabel(imageIcon);
    jCheckBox = new JCheckBox("Don't show me again");
    jLabel.setBounds((screenWidth / 2) - imageIcon.getIconWidth() / 2,
        (screenHeight / 2) - imageIcon.getIconHeight() / 2, imageIcon.getIconWidth(),
        imageIcon.getIconHeight());
    int w, h, bw, bh;
    w = (int) jCheckBox.getPreferredSize().getWidth();
    h = (int) jCheckBox.getPreferredSize().getHeight();
    bw = (int) jButton.getPreferredSize().getWidth();
    bh = (int) jButton.getPreferredSize().getHeight();
    jButton.setBounds(jLabel.getX() + (jLabel.getWidth() / 2) - (bw / 2),
        jLabel.getY() + jLabel.getHeight(), (int) jButton.getPreferredSize().getWidth(),
        (int) jButton.getPreferredSize().getHeight());
    jCheckBox.setBounds(jLabel.getX() + (jLabel.getWidth() / 2) - (w / 2),
        jLabel.getY() + jLabel.getHeight() + bh,
        w,
        h);
    add(jLabel);
    add(jButton);
    add(jCheckBox);
    jButton.addActionListener(this);
    jCheckBox.addActionListener(this);
    super.setOpaque(false);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == jButton) {
      JComponent comp = (JComponent) jButton;
      Window win = SwingUtilities.getWindowAncestor(comp);
      win.dispose();
    }
    if (e.getSource() == jCheckBox) {
      InitService.updateCheckBoxConfig(jCheckBox.isSelected());
    }

  }
}
