package com.gregory.learning.service;

import com.gregory.learning.utility.GifSequenceWriter;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GifMakerImpl implements GifMaker {

  private static final String IMAGE_DIRECTORY_NAME = "images";
  private static final String JPEG_TYPE = "jpg";
  private static final String IMAGE_SUFFIX = "." + JPEG_TYPE;
  private static final String META_DATA_IMAGE = "MetaData" + IMAGE_SUFFIX;
  private static final String GIF_NAME = "test";
  private static final String GIF_SUFFIX = ".gif";

  private static Path imageDirectory;

  private static final Logger LOGGER = LoggerFactory.getLogger(GifMakerImpl.class);

  static {
    try {
      imageDirectory = Paths
          .get(GifMakerImpl.class.getClassLoader().getResource(IMAGE_DIRECTORY_NAME).toURI());
    } catch (URISyntaxException e) {
      LOGGER.error(e.getMessage(), e.getCause());
    }
  }

  @Override
  public void createGif() throws IOException, AWTException {
    LOGGER.info("Gif maker called, from edt? : " + SwingUtilities.isEventDispatchThread());
    BufferedImage testImage = ImageIO.read(
        new File(imageDirectory.toString() + File.separator + META_DATA_IMAGE));

    ImageOutputStream outputStream = new FileImageOutputStream(
        new File(imageDirectory.toString() + File.separator + GIF_NAME + GIF_SUFFIX));

    GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, testImage.getType(),
        10, true);

    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    Robot robot = new Robot();

    int count = 0;

    while (count < 10) {

      BufferedImage image = robot.createScreenCapture(screenRect);
      ImageIO.write(image, JPEG_TYPE,
          new File(imageDirectory + File.separator + count + IMAGE_SUFFIX));

      gifSequenceWriter.writeToSequence(image);
      count++;
    }
    LOGGER.info("Closing gif writer and image output streams");
    gifSequenceWriter.close();
    outputStream.close();
  }
}
