package com.gregory.learning.service;

import java.awt.AWTException;
import java.awt.Cursor;
import java.io.IOException;

public interface GifMaker {

  void startRecording(int x, int y, int w, int h) throws IOException, AWTException;

  void stopRecording() throws IOException, AWTException;

  void setCursorInformation(Cursor cursorType, int x, int y);

}
