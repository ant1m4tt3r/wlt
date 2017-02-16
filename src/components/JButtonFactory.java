package components;

import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import utilities.FileUtilities;

public class JButtonFactory {

  /**
   * 
   * @param imagePath
   *          path of the image found
   * @param actionCommand
   *          used to display the altText, the text displayed when no image found and the action command that the button responds
   * @return JButton
   * @see JButton
   */
  public static JButton createToolButton(String imagePath, String actionCommand, ActionListener listener) {
    // Look for the image.
    URL imageURL = FileUtilities.getInstance().getClass().getResource(imagePath);

    // Create and initialize the button.
    JButton button = new JButton();
    button.setActionCommand(actionCommand);
    button.setFocusable(false);
    button.setToolTipText(actionCommand);
    button.addActionListener(listener);
    if (imageURL != null) { // image found
      button.setIcon(new ImageIcon(imageURL, actionCommand));
    }
    else { // no image found
      button.setText(actionCommand);
      System.err.println("Resource not found: " + imagePath);
    }
    return button;
  }
  
  /**
   * 
   * @param imagePath
   *          path of the image found
   * @param actionCommand
   *          used to display the altText, the text displayed when no image found and the action command that the button responds
   * @return JButton
   * @see JButton
   */
  public static JToggleButton createToolToggleButton(String imagePath, String actionCommand, ActionListener listener) {
    // Look for the image.
    URL imageURL = FileUtilities.getInstance().getClass().getResource(imagePath);

    // Create and initialize the button.
    JToggleButton button = new JToggleButton();
    button.setActionCommand(actionCommand);
    button.setFocusable(false);
    button.setToolTipText(actionCommand);
    button.addActionListener(listener);
    if (imageURL != null) { // image found
      button.setIcon(new ImageIcon(imageURL, actionCommand));
    }
    else { // no image found
      button.setText(actionCommand);
      System.err.println("Resource not found: " + imagePath);
    }
    return button;
  }

}
