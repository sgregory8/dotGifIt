package com.gregory.learning;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) throws AWTException, IOException {

    List<BufferedImage> imageList = new ArrayList<>();

    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    Robot robot = new Robot();

    System.out.println("getting screen images...");

    int count = 0;

    while (count < 10) {

      BufferedImage image = robot.createScreenCapture(screenRect);
      imageList.add(image);
      ImageIO.write(image, "jpg",
          new File("/users/gregory1/learning/java-projects/dot-gif-it/images/" + count + ".jpg"));

      count++;

    }
  }
}
