package com.gregory.learning.Gui.panels;

import com.gregory.learning.Gui.recording.BackgroundPane;
import com.gregory.learning.service.GifMaker;
import com.gregory.learning.service.InitService;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LeftPanel extends JPanel implements ActionListener, WindowListener {

  private JButton settingsButton;
  private JButton folderButton;
  private JButton recordButton;
  private JFrame demoFrame;
  private JFrame darkFrame;
  private JFrame settingsFrame;
  private JPanel backgroundFrame;
  private JLabel jLabel;

  private GifMaker gifMaker;

  public LeftPanel(GifMaker gifMaker) {
    this.gifMaker = gifMaker;
    setLayout(new GridLayout(2, 1));
    settingsButton = new JButton("Settings");
    folderButton = new JButton("Set default directory");
    recordButton = new JButton("Record gif");
    settingsButton.addActionListener(this);
    folderButton.addActionListener(this);
    recordButton.addActionListener(this);
    this.add(folderButton);
    this.add(recordButton);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton) e.getSource();
    if (clicked == settingsButton) {
      settingsFrame = new SettingsPanel();
      settingsFrame.setVisible(true);
      settingsFrame.setResizable(true);
    }
    if (clicked == folderButton) {
      JFileChooser jFileChooser = new JFileChooser();
      jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      int result = jFileChooser.showOpenDialog(this);

      if (result == JFileChooser.APPROVE_OPTION) {
        File selectedDirectory = jFileChooser.getSelectedFile();
        InitService.updateConfig(selectedDirectory.getAbsolutePath());
      }

    }
    if (clicked == recordButton) {
      if (!InitService.dontShowDemo()) {
        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.setVisible(false);
        demoFrame = new JFrame();
        demoFrame.addWindowListener(this);
        demoFrame.setUndecorated(true);
        demoFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        demoFrame.setBackground(new Color(0, 0, 0, 0));

        try {
          demoFrame.add(new DemoPanel());
        } catch (URISyntaxException ex) {
          ex.printStackTrace();
        }
        demoFrame.pack();
        demoFrame.setAlwaysOnTop(true);
        demoFrame.setVisible(true);
      } else {
        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.setVisible(false);
        darkFrame = new JFrame();
        darkFrame.setFocusable(true);
        darkFrame.addKeyListener(new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {

          }

          @Override
          public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
              try {
                gifMaker.stopRecording();
              } catch (IOException ex) {
                ex.printStackTrace();
              } catch (AWTException ex) {
                ex.printStackTrace();
              }
              darkFrame.dispose();
            }
          }

          @Override
          public void keyReleased(KeyEvent e) {

          }
        });
        darkFrame.addWindowListener(this);
        darkFrame.setUndecorated(true);
        darkFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        darkFrame.setBackground(new Color(0, 0, 0, 0));

        darkFrame.add(new BackgroundPane(gifMaker));

        darkFrame.pack();
        darkFrame.setAlwaysOnTop(true);
        darkFrame.setVisible(true);
      }
    }
  }

  @Override
  public void windowOpened(WindowEvent e) {

  }

  @Override
  public void windowClosing(WindowEvent e) {

  }

  @Override
  public void windowClosed(WindowEvent e) {
    JComponent comp = (JComponent) recordButton;
    Window win = SwingUtilities.getWindowAncestor(comp);
    if (e.getSource() == demoFrame) {
      demoFrame.dispose();
      darkFrame = new JFrame();
      darkFrame.addWindowListener(this);
      darkFrame.setFocusable(true);
      darkFrame.addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
          if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            darkFrame.dispose();
          }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
      });
      darkFrame.setUndecorated(true);
      darkFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      darkFrame.setBackground(new Color(0, 0, 0, 0));

      darkFrame.add(new BackgroundPane(gifMaker));

      darkFrame.pack();
      darkFrame.setAlwaysOnTop(true);
      darkFrame.setVisible(true);
    } else {
      win.setVisible(true);
      darkFrame.dispose();
    }

  }

  @Override
  public void windowIconified(WindowEvent e) {

  }

  @Override
  public void windowDeiconified(WindowEvent e) {

  }

  @Override
  public void windowActivated(WindowEvent e) {

  }

  @Override
  public void windowDeactivated(WindowEvent e) {

  }

}
