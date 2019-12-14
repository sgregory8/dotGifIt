package com.gregory.learning;

import com.gregory.learning.Gui.UIContainer;
import java.awt.AWTException;
import java.awt.EventQueue;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DotGifItApplication implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(DotGifItApplication.class);

  private final UIContainer uiContainer;

  @Autowired
  public DotGifItApplication(UIContainer UIContainer) {
    this.uiContainer = UIContainer;
  }

  public static void main(final String[] args) {
    new SpringApplicationBuilder(DotGifItApplication.class)
        .web(WebApplicationType.NONE)
        .headless(false)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
    SpringApplication.run(DotGifItApplication.class, args);
  }

  @Override
  public void run(String... args) throws IOException, AWTException {

    EventQueue.invokeLater(() -> {

      uiContainer.runApp();
      uiContainer.setVisible(true);

    });

  }
}
