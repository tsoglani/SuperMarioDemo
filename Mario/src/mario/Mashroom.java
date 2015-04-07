/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author gaitanesnikos
 */
public class Mashroom extends JPanel implements Runnable, Rec {

    private MarioPanel panel;
    private Random random = new Random();
    private Image image;

    public Mashroom(int locx, int locy, MarioPanel panel) {
        this.panel = panel;
        image = getToolkit().createImage("mushroom.png");
        setSize(35, 39);
        setLocation(locx, locy);
    }

    @Override
    public void run() {
        boolean goRigt = random.nextBoolean();
        while (getLocation().y < MFrame.height) {
            try {
                // boolean intersectWall = false;// nam min pernaei mesa apo ton toixo ston aksona xx
                boolean intersectFloor = false;// nam min pernaei mesa apo ton toixo ston aksona yy
                Thread.sleep(5);
                // pros ta pou tha pigenei to manitari
                for (int i = 0; i < MarioPanel.stage.size(); i++) {// nam min pernaei mesa apo ton toixo ston aksona xx
                    if (MarioPanel.stage.get(i).getRectangle().intersects(getRectangle().x, getRectangle().y + getHeight() / 2 - getHeight() / 5, getWidth(), getHeight() / 5)) {
                        //   intersectWall = false;

                        goRigt = !goRigt;

                    }
                }


                for (int i = 0; i < MarioPanel.gifts.size(); i++) {// nam min pernaei mesa apo ton toixo ston aksona xx
                    if (MarioPanel.gifts.get(i).getRectangle().intersects(getRectangle().x, getRectangle().y + getHeight() / 2 - getHeight() / 5, getWidth(), getHeight() / 5)) {
                        intersectFloor = true;

                    }
                }
                for (int i = 0; i < MarioPanel.stage.size(); i++) {// nam min pernaei mesa apo ton toixo ston aksona xx
                    if (MarioPanel.stage.get(i).getRectangle().intersects(getRectangle().x + getWidth() / 2 - getWidth() / 5, getRectangle().y, getWidth() / 5, getHeight())) {
                        intersectFloor = true;
                    }
                }



                if (goRigt) {

                    //  if (!intersectWall) {
                    setLocation(getLocation().x + 1, getLocation().y);
                    // }
                    // getLocation().x++;
                } else { //if (!intersectWall) {
                    setLocation(getLocation().x - 1, getLocation().y);
                    //  getLocation().x--;
                    // }
                }

                if (!intersectFloor) {
                    setLocation(getLocation().x, getLocation().y + 1);
                }

                // to manitari na akoumpaei to patwma

                if (panel.getMario().getRectangle().intersects(getRectangle())) {
                    panel.remove(this);
                    if (!panel.getMario().isHasTakeMashroom()) {
                        panel.getMario().setHasTakeMashroom(true);
                        panel.getMario().setLocation(panel.getMario().getLocation().x, panel.getMario().getLocation().y - panel.getMario().getWidth());
                    }
                    System.out.println("you take mashroom");
                    break;
                }
                if (getLocation().y >= MFrame.height) {
                    System.out.println("mashroom falls");
                    break;
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("mashroom killed");

    }

    public Rectangle getRectangle() {
        return new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight());
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.fill(getRectangle());
        g2d.drawImage(image, 0, 0, this);
    }
}
