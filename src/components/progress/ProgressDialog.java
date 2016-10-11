package components.progress;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import components.JButtonFactory;
import utilities.Utilities;

public class ProgressDialog extends JDialog {

  /**
   * 
   */
  private static final long serialVersionUID = 3535734683054012739L;

  private List<JProgressBar> listProgressBar;
  private Task task;
  private List<Progress> listInterfaceProgress;
  private List<JLabel> listLabel;
  JButton btnOK;

  public void init(String title,List<Progress> listInterfaceProgress) {
    setUndecorated(true);
    setTitle(title);
    setModal(true);
    setModalityType(ModalityType.DOCUMENT_MODAL);    
    this.listInterfaceProgress = listInterfaceProgress;
    int height= listInterfaceProgress.size()*70+100;
    setLayout(new GridBagLayout());
    setSize(400, height);
    Utilities.centralizarComponente(this);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    
    GridBagConstraints cons = new GridBagConstraints();
    cons.fill = GridBagConstraints.BOTH;
    cons.gridx = 0;
    
    cons.weightx = 1;    
    cons.anchor = GridBagConstraints.CENTER;
    cons.insets = new Insets(10, 10, 10, 10);
    listProgressBar = new ArrayList<JProgressBar>();
    int gridy = 0;
    listLabel = new ArrayList<JLabel>();
    for (int i =0;i<listInterfaceProgress.size();i++) {
      Progress interfaceProgress = listInterfaceProgress.get(i);
      JProgressBar progressBar = new JProgressBar(0,100);
      progressBar.setValue(0);      
      JLabel label = new JLabel(interfaceProgress.getProgressName());
      listLabel.add(label);
      progressBar.setStringPainted(true);
      listProgressBar.add(progressBar);
      
      //position
      cons.insets = new Insets(10, 10, 0, 10);
      cons.gridy = gridy;
      add(label, cons);
      
      gridy++;
      cons.gridy = gridy;
      cons.insets = new Insets(0, 10, 10, 10);
      add(progressBar,cons);
      gridy++;
    }
    
    btnOK = JButtonFactory.createToolButton("ok32", "OK", new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
      }
    });
    btnOK.setEnabled(false);
    
    cons.insets = new Insets(10, 10, 10, 10);
    cons.gridy = gridy;
    cons.fill = GridBagConstraints.NONE;
    add(btnOK,cons);
    task = new Task();
    task.start();
    setVisible(true);
  }
  
  public ProgressDialog(String title,List<Progress> listInterfaceProgress) {
    setModalityType(ModalityType.APPLICATION_MODAL);
    init(title,listInterfaceProgress);
  }
  public ProgressDialog(String title,List<Progress> listInterfaceProgress,JFrame frame) {
    super(frame);
    init(title,listInterfaceProgress);
    
  }

  private class Task extends Thread {
    private boolean allOK =false;
    public Task() {
    }


    public void run() {
      
      while (!allOK) {

        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            boolean ok = true;
            for (int i=0;i<listInterfaceProgress.size();i++) {
              int progress = listInterfaceProgress.get(i).getProgress();
              listProgressBar.get(i).setValue(progress);   
              listLabel.get(i).setText(listInterfaceProgress.get(i).getProgressName());
              if(progress <100)
                ok = false;
              
            }
            allOK = ok;
          }
        });
        try {
          Thread.sleep(100);
        }
        catch (InterruptedException e) {
        }
      }
      btnOK.setEnabled(true);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
  }
}
