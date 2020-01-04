package com.gregory.learning.Gui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SettingsPanel extends JFrame implements ActionListener {

  private JButton fileButton;
  private JLabel jLabel;

  public SettingsPanel() {
    setLayout(new BorderLayout());
    fileButton = new JButton("File");
    jLabel = new JLabel("Set the default save directory");
    add(fileButton, BorderLayout.EAST);
    add(jLabel, BorderLayout.WEST);
    fileButton.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton) e.getSource();
    if (clicked == fileButton) {
      System.out.println("clicked");
      JFileChooser jFileChooser = new JFileChooser();
      jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      int result = jFileChooser.showOpenDialog(this);

      if (result == JFileChooser.APPROVE_OPTION) {
        File selectedDirectory = jFileChooser.getSelectedFile();
        System.out.println("Selected file: " + selectedDirectory.getAbsolutePath());
      }

    }
  }

}
