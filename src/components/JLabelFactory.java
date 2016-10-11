package components;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class JLabelFactory {

  public static JLabel createLabeledIcon(String imageName, String toolTip) {
    // Look for the image.
    String imgLocation = "/imagens/" + imageName + ".png";
    URL imageURL = services.FileServices.getInstance().getClass().getResource(imgLocation);

    // Create and initialize the button.
    JLabel button = new JLabel();

    button.setFocusable(false);
    button.setToolTipText(toolTip);

    if (imageURL != null) { // image found

      button.setIcon(new ImageIcon(imageURL, toolTip));
    }
    else { // no image found

      button.setText(toolTip);
      System.err.println("Resource not found: " + imgLocation);
    }
    return button;
  }

}
