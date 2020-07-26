package com.gregory.learning.service;

import static com.gregory.learning.service.InitService.DOT_GIF_IT_HOME;
import static com.gregory.learning.service.InitService.GIF_PROPERTIES;
import static com.gregory.learning.service.InitService.HOME_DIRECTORY;

import com.gregory.learning.utility.GifSequenceWriter;
import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GifMakerImpl implements GifMaker {

  private static final String IMAGE_DIRECTORY_NAME = "images";
  private static final String JPEG_TYPE = "jpg";
  private static final String IMAGE_SUFFIX = "." + JPEG_TYPE;
  private static final String META_DATA_IMAGE = "MetaData" + IMAGE_SUFFIX;
  private static final String GIF_NAME = "test";
  private static final String GIF_SUFFIX = ".gif";

  private static Path imageDirectory;

  public void setRecording(boolean recording) {
    isRecording = recording;
  }

  private boolean isRecording = false;
  private Cursor currentCursor;
  private int cursorX, cursorY;

  @Override
  public void setCursorInformation(Cursor currentCursor, int x, int y) {
    this.currentCursor = currentCursor;
    this.cursorX = x;
    this.cursorY = y;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(GifMakerImpl.class);

  static {
//    try {
//      Path path = Paths.get(GifMakerImpl.class.getResource(File.separator + IMAGE_DIRECTORY_NAME).toURI());
//      if (input == null) {
//        // this is how we load file within editor (eg eclipse)
//        input = GifMakerImpl.class.getClassLoader().getResourceAsStream(File.separator + IMAGE_DIRECTORY_NAME);
//      }
//      imageDirectory = Paths
//          .get(GifMakerImpl.class.getClassLoader().getResource(IMAGE_DIRECTORY_NAME).toURI());
//    } catch (URISyntaxException e) {
//      LOGGER.error(e.getMessage(), e.getCause());
//    }
  }

  @Override
  public void startRecording(int x, int y, int w, int h) {
    isRecording = true;
    try {
      recorder(x, y, w, h);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stopRecording() {
    isRecording = false;
  }

  private void recorder(int x, int y, int w, int h) throws IOException, AWTException {
    LOGGER.info("Gif maker called, from edt? : " + SwingUtilities.isEventDispatchThread());
    BufferedImage testImage = ImageIO.read(getClass().getResource("/" + IMAGE_DIRECTORY_NAME + "/" + META_DATA_IMAGE));

    Properties prop = new Properties();
    FileInputStream ip = new FileInputStream(
        System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME + File.separator
            + GIF_PROPERTIES);
    prop.load(ip);
    String fileNameToSaveTo = prop.get(HOME_DIRECTORY).toString();
    System.out.println(fileNameToSaveTo);

    ImageOutputStream outputStream = new FileImageOutputStream(
        new File(fileNameToSaveTo + File.separator + System.currentTimeMillis() + GIF_SUFFIX));

    GifSequenceWriter gifSequenceWriter = new GifSequenceWriter(outputStream, testImage.getType(),
        10, true);

    Rectangle screenRect = new Rectangle(x, y, w, h);
    Robot robot = new Robot();

    int count = 0;

    while (isRecording) {

      BufferedImage image = robot.createScreenCapture(screenRect);
//      ImageIO.write(image, JPEG_TYPE,
//          new File(imageDirectory + File.separator + count + IMAGE_SUFFIX));

      gifSequenceWriter.writeToSequence(image);
      System.out.println("called to write to sequence at: " + System.currentTimeMillis());
      count++;
    }
    LOGGER.info("Closing gif writer and image output streams");
    gifSequenceWriter.close();
    outputStream.close();
//    Thread.sleep();
  }
}
