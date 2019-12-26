package com.gregory.learning.service;

import java.awt.AWTException;
import java.io.IOException;

public interface GifMaker {

  void startRecording() throws IOException, AWTException;

  void stopRecording() throws IOException, AWTException;

}
