package com.gregory.learning.Gui.panels;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.springframework.stereotype.Component;

@Component
public class RightPanel extends JPanel implements ActionListener {

  private static final String GIF_DIRECTORY = "images";
  private static final String ICON_NAME = "test";
  private static final String ICON_TYPE = ".gif";

  private JLabel jLabel;

  private ImageIcon imageIcon;

  public RightPanel() throws URISyntaxException {
    this.imageIcon = new ImageIcon(Paths
        .get(RightPanel.class.getClassLoader()
            .getResource(GIF_DIRECTORY + File.separator + ICON_NAME + ICON_TYPE).toURI())
        .toString());

    Image image = imageIcon.getImage(); // transform it
    Image newimg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH);
    imageIcon = new ImageIcon(newimg);

    this.jLabel = new JLabel(imageIcon);
    this.add(jLabel);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
