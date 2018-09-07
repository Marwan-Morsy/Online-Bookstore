package Forms;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel
{
    public void paintComponent(Graphics g)
    {
        Image image=new ImageIcon("MainViewPic.jpg").getImage();
        Image welcomeImage = new ImageIcon("Welcome.png").getImage();
        Image newImage = welcomeImage.getScaledInstance(300, 100, Image.SCALE_DEFAULT);
        g.drawImage(image,0,0,this);
        g.drawImage(welcomeImage,0,0,this);

    }
}