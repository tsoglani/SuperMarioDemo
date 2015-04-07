/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author gaitanesnikos
 */
public class Gift extends JPanel implements Runnable,Rec {

    private Thread thread;
    private boolean isEmpty;
    private int posx, posy;
    private Image image;
    private SuperMario mario;
    private MarioPanel panel;
    private Image emptyImage;

    public Gift(int posx, int posy, MarioPanel panel) {
        this.posx = posx;
        this.panel = panel;
        mario = panel.getMario();
        this.posy = posy;
        setLocation(posx, posy);
        setSize(27, 27);
        image = getToolkit().createImage("gift.png");
        emptyImage = getToolkit().createImage("emptyGift.png");
    }

    /**
     *
     */
    public void startThread() {
        thread = new Thread(this);
        panel.add(this);
        thread.start();
    }

    /**
     *
     */
    public void startingPoint() {

        setLocation(posx, posy);
        isEmpty = false;
        if(!thread.isAlive())
        startThread();
    }

    /**
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int posx = 0, posy = 0;
        if (isEmpty) {
            posx = -224;
            posy = -275;
            // emptyImage = getToolkit().createImage("emptyGift.png");
            g.drawImage(emptyImage, posx, posy, this);
        } else {
            g.drawImage(image, posx, posy, this);
        }
    }

    @Override
    public void run() {
        while (!isEmpty) {
            try {
                Thread.sleep(10);


                if (mario.getJumpRectangle().intersects(getRectangle2())) {
                    System.out.println("you take a present");
                    isEmpty = true;
                    Mashroom mashroom = new Mashroom(getLocation().x, getLocation().y - 30, panel);
                    new Thread(mashroom).start();
                    panel.add(mashroom);
                    MarioPanel.mashrooms.add(mashroom);
                    MarioPanel.world.add(mashroom);
                    break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Gift.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        panel.repaint();
    }

    /**
     *
     * @return
     */
    public Rectangle getRectangle() {
        return new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight());

    }

    /**
     *
     * @return
     */
    public Rectangle getRectangle2() {
        return new Rectangle(getLocation().x, getLocation().y + getHeight(), getWidth(), 10);

    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
