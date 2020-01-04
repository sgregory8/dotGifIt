package com.gregory.learning;

import com.gregory.learning.Gui.UIContainer;
import com.gregory.learning.Gui.panels.LeftPanel;
import com.gregory.learning.Gui.panels.RightPanel;
import com.gregory.learning.service.GifMakerImpl;
import com.gregory.learning.service.InitService;
import java.awt.EventQueue;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DotGifItApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(DotGifItApplication.class);

  public static void main(final String[] args) {
    InitService.initDotGifItFile();
    InitService.initSaveConfig();
    EventQueue.invokeLater(() -> {
      try {
        UIContainer uiContainer = new UIContainer(new LeftPanel(new GifMakerImpl()),
            new RightPanel());
        uiContainer.runApp();
        uiContainer.setVisible(true);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    });
  }

}
