package handler.creator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.progress.Progress;
import utilities.Utilities;

public class CreatorView implements ActionListener, Progress {

  BeanCreator bc = new BeanCreator();
  JFrame f = new JFrame("Create Bean");
  JPanel p = new JPanel();
  JLabel xlsx;
  JLabel out;
  JTextField excel;
  JTextField java;
  JTextField focused;
  JButton ok;
  JButton exit;
  JFileChooser chooser;
  JLabel bar;
  JButton ch;

  int progress = 0;

  private static final String ACTION_OK = "Ok";
  private static final String ACTION_EXIT = "Exit";
  private static final String ACTION_CHOOSE = "Find";


  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case ACTION_OK:
        if (validate())
          bc.createBean(excel.getText(), java.getText());
        break;
      case ACTION_EXIT:
        System.exit(0);
        break;
      case ACTION_CHOOSE:
        showChooser();
        break;
    }
  }


  private void showChooser() {
    FileFilter filter = new FileNameExtensionFilter("xlsx", "xlsx");
    chooser = new JFileChooser(System.getProperty("user.dir", "."));
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.addChoosableFileFilter(filter);
    chooser.setDialogTitle("Select the table");
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    if (JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(null)) {
      File selected = chooser.getSelectedFile();
      excel.setText(selected.getAbsolutePath());
      String javaText = selected.getAbsolutePath().substring(0, selected.getAbsolutePath().lastIndexOf("\\") + 1) + selected.getName().substring(0, selected.getName().lastIndexOf(".") + 1) + "java";
      java.setText(javaText);
    }
  }


  private boolean validate() {
    if (excel.getText().isEmpty() || java.getText().isEmpty()) {
      JOptionPane.showMessageDialog(null, "Fill both camp.");
      return false;
    }
    return true;
  }


  public static void main(String[] args) {
    CreatorView v = new CreatorView();
    v.mainScreen();
  }


  private void mainScreen() {
    addComps();
    f.add(p);
    f.setResizable(false);
    f.setSize(250, 350);
    Utilities.centralizarComponente(f);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
  }


  private void addComps() {
    p.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(2, 10, 2, 10);
    c.gridx = 0;
    c.gridy = 0;

    xlsx = new JLabel("Excel file");
    p.add(xlsx, c);

    c.gridwidth = 2;
    c.gridy++;

    excel = new JTextField();
    p.add(excel, c);

    c.gridy++;

    out = new JLabel("Java file");
    p.add(out, c);

    c.gridy++;

    java = new JTextField();
    p.add(java, c);

    c.gridy++;

    ch = new JButton();
    ch.setText(ACTION_CHOOSE);
    ch.setActionCommand(ACTION_CHOOSE);
    ch.addActionListener(this);
    p.add(ch, c);

    c.insets = new Insets(75, 10, 2, 10);
    c.anchor = GridBagConstraints.CENTER;

    bar = new JLabel();

    { // Thread para atualização do Label.
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            while (true) {
              Thread.sleep(100);
              bar.setText(getProgressName());
            }
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
      }).start();
    }

    p.add(bar, c);

    c.insets = new Insets(2, 10, 2, 10);
    c.gridy++;
    c.gridwidth = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.WEST;

    ok = new JButton();
    ok.setText(ACTION_OK);
    ok.setActionCommand(ACTION_OK);
    ok.addActionListener(this);
    p.add(ok, c);

    c.gridx++;

    exit = new JButton();
    exit.setText(ACTION_EXIT);
    exit.setActionCommand(ACTION_EXIT);
    exit.addActionListener(this);
    p.add(exit, c);
  }


  @Override
  public int getProgress() {
    return progress;
  }


  @Override
  public String getProgressName() {
    this.progress = bc.getProgress();
    if (progress == 0)
      return "Aguardando";
    else if (progress < 100)
      return "<html><font color=green><b>Criando arquivo</b></font></html>";
    else
      return "<html><font color=red><b>Finalizado</b></font></html>";
  }

}
