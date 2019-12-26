package com.gregory.learning.Gui.panels;

import com.gregory.learning.Gui.recording.BackgroundPane;
import com.gregory.learning.Gui.recording.ResizableRectangle;
import com.gregory.learning.service.GifMaker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LeftPanel extends JPanel implements ActionListener {

  private JButton settingsButton;
  private JButton folderButton;
  private JButton recordButton;
  private ResizableRectangle resizableRectangle;
  private JFrame darkFrame;

  private GifMaker gifMaker;

  @Autowired
  public LeftPanel(GifMaker gifMaker) {
    this.gifMaker = gifMaker;
    setLayout(new GridLayout(3, 1));
    settingsButton = new JButton("Settings");
    folderButton = new JButton("Folder");
    recordButton = new JButton("Record");
    settingsButton.addActionListener(this);
    folderButton.addActionListener(this);
    recordButton.addActionListener(this);
    this.add(settingsButton);
    this.add(folderButton);
    this.add(recordButton);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton) e.getSource();
    if (clicked == settingsButton) {

    }
    if (clicked == folderButton) {
      JFileChooser jFileChooser = new JFileChooser();
      jFileChooser.showOpenDialog(this);
    }
    if (clicked == recordButton) {
      darkFrame = new JFrame();
      darkFrame.setUndecorated(true);
      darkFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      darkFrame.setBackground(new Color(0, 0, 0, 0));
      darkFrame.setLayout(new BorderLayout());
      darkFrame.add(new BackgroundPane(gifMaker));
      darkFrame.pack();
      darkFrame.setLocationRelativeTo(null);
      darkFrame.setVisible(true);
    }
  }
}
