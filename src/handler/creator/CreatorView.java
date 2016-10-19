package handler.creator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.progress.Progress;
import utilities.Utilities;

public class CreatorView extends JFrame implements ActionListener, Progress {

  /**
   * 
   */
  private static final long serialVersionUID = 4052053560840587805L;
  private static final String ACTION_OK = "Ok";
  private static final String ACTION_EXIT = "Exit";
  private static final String ACTION_CHOOSE = "Find";

  
  private final String eclipseImagePath = "/resources/img/eclipse.png";

  private int progress = 0;

  private final BeanCreator bc = new BeanCreator();
  JPanel p = new JPanel();
  JLabel xlsx;
  JLabel out;
  JLabel eclipse;
  JTextField excel;
  JTextField java;
  JTextField focused;
  JButton ok;
  JButton exit;
  JFileChooser chooser;
  JLabel bar;
  JButton find;

  // Thread para atualização do label
  Thread t = new Thread(new Runnable() {
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
  });;


  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new CreatorView();
      }
    });
  }


  private CreatorView() {
    super("Create Bean");
    addComps();
    this.add(p);
    this.setResizable(false);
    this.setSize(270, 370);
    Utilities.centralizarComponente(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }


  private void addComps() {
    p.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(20, 20, 2, 20);
    c.gridx = 0;
    c.gridy = 0;

    xlsx = new JLabel("Excel file");
    p.add(xlsx, c);

    c.insets = new Insets(2, 20, 2, 20);
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
    c.insets = new Insets(30, 20, 2, 20);
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.HORIZONTAL;

    bar = new JLabel();
    p.add(bar, c);

    c.gridy++;
    c.insets = new Insets(2, 20, 2, 20);

    find = new JButton();
    find.setText(ACTION_CHOOSE);
    find.setActionCommand(ACTION_CHOOSE);
    find.addActionListener(this);
    p.add(find, c);

    c.insets = new Insets(2, 20, 10, 20);
    c.gridy++;
    c.gridwidth = 1;
    c.weightx = 50;
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

    c.gridy++;
    c.gridx = 0;
    c.gridwidth = 2;
    
    JSeparator separator = new JSeparator();
    separator.setBounds(1, exit.getHeight()+20, super.getWidth()-1, 1);
    p.add(separator, c);
    
    c.gridy++;
    c.weightx = 0.1;
    c.weighty = 0.1;
    c.insets = new Insets(2, 10, 2, 10);
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.HORIZONTAL;

    URL imageURL = getClass().getResource(eclipseImagePath);
    eclipse = new JLabel(new ImageIcon(imageURL));
    p.add(eclipse, c);
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


  private boolean verify() {
    if (excel.getText().isEmpty() || java.getText().isEmpty()) {
      JOptionPane.showMessageDialog(null, "Fill both camp.");
      return false;
    }
    return true;
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case ACTION_OK:
        if (verify()) {
          t.start();
          bc.createBean(excel.getText(), java.getText());
        }
        break;
      case ACTION_EXIT:
        System.exit(0);
        break;
      case ACTION_CHOOSE:
        showChooser();
        break;
    }
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
