package com.gregory.learning.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

public class InitService {

  public static final String DOT_GIF_IT_HOME = "dotGifIt";
  public static final String GIF_DIRECTORY = "Gifs";
  public static final String GIF_PROPERTIES = "dotGifIt.properties";
  public static final String HOME_DIRECTORY = "gif.home";
  public static final String SHOW_DEMO = "gif.showDemo";

  public static void initDotGifItFile() {
    new File(System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME).mkdir();
    new File(System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME + File.separator
        + GIF_DIRECTORY).mkdir();
  }

  public static void initSaveConfig() {
    File appConfig = new File(
        System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME + File.separator
            + GIF_PROPERTIES);

    if (!appConfig.exists()) {

      Properties prop = new Properties();

      try (Writer inputStream = new FileWriter(appConfig)) {

        // Setting the properties.
        prop.setProperty("gif.home",
            System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME + File.separator
                + GIF_DIRECTORY);

        // Storing the properties in the file with a heading comment.
        prop.store(inputStream, "dotGifIt application information");
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void updateConfig(String fileName) {
    try {
      File appConfig = new File(
          System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME + File.separator
              + GIF_PROPERTIES);
      FileInputStream propsInput = new FileInputStream(
          System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME + File.separator
              + GIF_PROPERTIES);

      Properties prop = new Properties();

      prop.load(propsInput);

      try (Writer inputStream = new FileWriter(appConfig)) {

        // Setting the properties.
        prop.setProperty(HOME_DIRECTORY,
            fileName);

        // Storing the properties in the file with a heading comment.
        prop.store(inputStream, "dotGifIt application information");
      } catch (IOException ex) {
        ex.printStackTrace();
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void updateCheckBoxConfig(Boolean showDemo) {
    try {
      File appConfig = new File(
          System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME + File.separator
              + GIF_PROPERTIES);
      FileInputStream propsInput = new FileInputStream(
          System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME + File.separator
              + GIF_PROPERTIES);

      Properties prop = new Properties();

      prop.load(propsInput);

      try (Writer inputStream = new FileWriter(appConfig)) {

        // Setting the properties.
        System.out.println("setting to: " + showDemo);
        prop.setProperty(SHOW_DEMO,
            showDemo.toString());

        // Storing the properties in the file with a heading comment.
        prop.store(inputStream, "dotGifIt application information");
      } catch (IOException ex) {
        ex.printStackTrace();
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Boolean dontShowDemo() {
    try {
      FileInputStream propsInput = new FileInputStream(
          System.getProperty("user.home") + File.separator + DOT_GIF_IT_HOME + File.separator
              + GIF_PROPERTIES);

      Properties prop = new Properties();

      prop.load(propsInput);
      return Boolean.valueOf(prop.get(SHOW_DEMO).toString());
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
}
