package com.gregory.learning.Gui.panels;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import org.springframework.stereotype.Component;

@Component
public class RightPanel extends JPanel implements ActionListener {

  private TextArea textArea;

  public RightPanel() {
    this.textArea = new TextArea();
    this.add(textArea);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
