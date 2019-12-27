package com.gregory.learning.service;

import java.awt.AWTException;
import java.io.IOException;

public interface GifMaker {

  void startRecording(int x, int y, int w, int h) throws IOException, AWTException;

  void stopRecording() throws IOException, AWTException;

}
