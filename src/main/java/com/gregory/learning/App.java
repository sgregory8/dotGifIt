package com.gregory.learning;

import com.gregory.learning.utility.GifSequenceWriter;
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
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageOutputStreamImpl;

public class App {

  public static void main(String[] args) throws AWTException, IOException {

    List<BufferedImage> imageList = new ArrayList<>();

    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    Robot robot = new Robot();

    System.out.println("getting screen images...");

    int count = 0;

    BufferedImage testImage = ImageIO.read(
        new File("/users/gregory1/learning/java-projects/dot-gif-it/images/" + "MetaData.jpg"));

    ImageOutputStream output = new FileImageOutputStream(
        new File("/users/gregory1/learning/java-projects/dot-gif-it/images/" + "test.gif"));

    GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(output, testImage.getType(), 10, true);

    while (count < 10) {

      BufferedImage image = robot.createScreenCapture(screenRect);
      imageList.add(image);
      ImageIO.write(image, "jpg",
          new File("/users/gregory1/learning/java-projects/dot-gif-it/images/" + count + ".jpg"));
      gifSequenceWriter.writeToSequence(image);
      count++;
    }
    gifSequenceWriter.close();
    output.close();

  }
}
