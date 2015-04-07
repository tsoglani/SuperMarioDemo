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
public class Enemy extends JPanel implements Runnable, Rec {

    private int posx, posy;
    private Image image;
    private MarioPanel panel;
    private Thread thread;

    public Enemy(int posx, int posy, MarioPanel panel) {
        this.posx = posx;
        this.posy = posy;
        this.panel = panel;
        setSize(35, 35);
        setLocation(posx, posy);
        image = getToolkit().createImage("enemy2.png");

    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        while (true) {
            boolean goDown = false;
            boolean goRight = false;
            try {
                Thread.sleep(8);
            } catch (InterruptedException ex) {
                Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
            }
            // pros ta pou tha pigenei to manitari
            for (int i = 0; i < MarioPanel.stage.size(); i++) {// nam min pernaei mesa apo ton toixo ston aksona xx
                if (MarioPanel.stage.get(i).getRectangle().intersects(getRectangle().x, getRectangle().y + getHeight() / 2 - getHeight() / 5, getWidth(), getHeight() / 5)) {
                    //   intersectWall = false;

                    goRight = !goRight;

                }
            }


            for (int i = 0; i < MarioPanel.gifts.size(); i++) {// nam min pernaei mesa apo ton toixo ston aksona xx
                if (MarioPanel.gifts.get(i).getRectangle().intersects(getRectangle().x, getRectangle().y + getHeight() / 2 - getHeight() / 5, getWidth(), getHeight() / 5)) {
                    goDown = true;

                }
            }
            for (int i = 0; i < MarioPanel.stage.size(); i++) {// nam min pernaei mesa apo ton toixo ston aksona yy
                if (MarioPanel.stage.get(i).getRectangle().intersects(getRectangle().x + getWidth() / 2 - getWidth() / 5, getRectangle().y, getWidth() / 5, getHeight())) {
                    goDown = true;
                }
            }

            if (goRight) {

                //  if (!intersectWall) {
                setLocation(getLocation().x + 1, getLocation().y);
                // }
                // getLocation().x++;
            } else { //if (!intersectWall) {
                setLocation(getLocation().x - 1, getLocation().y);
                //  getLocation().x--;
                // }
            }

            if (!goDown) {// to manitari na akoumpaei to patwma
                setLocation(getLocation().x, getLocation().y + 1);
            }
            if (panel.getMario().getRectangle().intersects(getRectangle())) {
                if (panel.getMario().isHasTakeMashroom()) {
                    panel.getMario().setHasTakeMashroom(false);
                    System.out.println("now you don't have mashroom");
                    panel.remove(this);
                } else {
                    panel.getMario().looseLife();
                    System.out.println("lose a life");
                }
                break;
            }

            if (getLocation().y >= MFrame.height) {
                System.out.println("enemy falls");
                break;
            }

            if (panel.getMario().getRectangle().intersects(getWeekRectangle())) {// an o mario pidaei panw ston exthro kai ton eksontonei
                System.out.println("you kill the enemy");
                panel.getMario().setIsJumping(true);
                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        panel.getMario().setIsJumping(false);
                    }
                }.start();
                panel.remove(this);
                panel.repaint();
                break;
            }



        }

    }

    public void startThread() {

        // if (thread != null && thread.isAlive()) {

        thread = null;

        // }
        thread = new Thread(this);

        panel.add(this);
        thread.start();
    }

    public void startingPoint() {
        setLocation(posx, posy);
        startThread();
    }

    public Rectangle getRectangle() {
        return new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight());
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Rectangle getWeekRectangle() {
        return new Rectangle(getLocation().x + getWidth() / 2 - getWidth() / 4, getLocation().y - getHeight() / 5, getWidth() / 2, getHeight() / 5);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.red);
        //g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, -100, -375, this);

    }
}
