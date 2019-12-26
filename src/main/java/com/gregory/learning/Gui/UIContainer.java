package com.gregory.learning.Gui;

import static javax.swing.SwingUtilities.isEventDispatchThread;
import static javax.swing.SwingUtilities.isLeftMouseButton;

import com.gregory.learning.DotGifItApplication;
import com.gregory.learning.Gui.panels.LeftPanel;
import com.gregory.learning.Gui.panels.RightPanel;
import com.gregory.learning.service.GifMaker;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UIContainer extends JFrame {

  private static final String ICON_DIRECTORY = "icons";
  private static final String ICON_NAME = "icon";
  private static final String ICON_TYPE = ".png";

  private static final Logger LOGGER = LoggerFactory.getLogger(DotGifItApplication.class);

  private final LeftPanel leftPanel;

  private final RightPanel rightPanel;

  private ImageIcon imageIcon;

  @Autowired
  public UIContainer(LeftPanel leftPanel, RightPanel rightPanel)
      throws HeadlessException {
    this.leftPanel = leftPanel;
    this.rightPanel = rightPanel;
    try {
      this.imageIcon = new ImageIcon(Paths
          .get(UIContainer.class.getClassLoader()
              .getResource(ICON_DIRECTORY + File.separator + ICON_NAME + ICON_TYPE).toURI())
          .toString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public void runApp() {
    initUI();
  }

  private void initUI() {

    setLayout(new BorderLayout());
    setResizable(false);

    add(leftPanel, BorderLayout.WEST);
    add(rightPanel, BorderLayout.EAST);


    setTitle("dot GIF it!");
    setSize(300, 200);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setIconImage(imageIcon.getImage());
  }
}