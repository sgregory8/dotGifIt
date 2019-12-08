package com.gregory.learning.graphics;

import com.gregory.learning.DotGifItApplication;
import com.gregory.learning.service.GifMaker;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UIContainer extends JFrame {

  private static final Logger LOGGER = LoggerFactory.getLogger(DotGifItApplication.class);

  private final GifMaker gifMaker;

  @Autowired
  public UIContainer(GifMaker gifMaker) throws HeadlessException {
    this.gifMaker = gifMaker;
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
