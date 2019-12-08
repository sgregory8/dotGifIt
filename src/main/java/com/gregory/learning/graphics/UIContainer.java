package com.gregory.learning.graphics;

import com.gregory.learning.DotGifItApplication;
import com.gregory.learning.service.GifMaker;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UIContainer extends JFrame {

  private static final String ICON_DIRECTORY = "icons";
  private static final String ICON_NAME = "icon";
  private static final String ICON_TYPE = ".jpg";

  private static final Logger LOGGER = LoggerFactory.getLogger(DotGifItApplication.class);

  private final GifMaker gifMaker;

  private ImageIcon imageIcon;

  @Autowired
  public UIContainer(GifMaker gifMaker) throws HeadlessException {
    this.gifMaker = gifMaker;
    try {
      this.imageIcon = new ImageIcon(Paths
          .get(UIContainer.class.getClassLoader()
              .getResource(ICON_DIRECTORY + File.separator + ICON_NAME + ICON_TYPE).toURI()).toString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public void runApp() {
    initUI();
  }

  private void initUI() {
    var quitButton = new JButton("Record Gif");

    quitButton.addActionListener((event) -> {
      try {
        gifMaker.createGif();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (AWTException e) {
        e.printStackTrace();
      }
      System.exit(0);
    });

    createLayout(quitButton);

    setTitle("Gif Recorder");
    setSize(300, 200);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setIconImage(imageIcon.getImage());
  }

  private void createLayout(JComponent... arg) {

    var pane = getContentPane();
    var gl = new GroupLayout(pane);
    pane.setLayout(gl);

    gl.setAutoCreateContainerGaps(true);

    gl.setHorizontalGroup(gl.createSequentialGroup()
        .addComponent(arg[0])
    );

    gl.setVerticalGroup(gl.createSequentialGroup()
        .addComponent(arg[0])
    );
  }
}
