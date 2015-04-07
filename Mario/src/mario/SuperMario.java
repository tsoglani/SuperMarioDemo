/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mario;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Line;
import javax.swing.JPanel;

/**
 *
 * @author gaitanesnikos
 */
public class SuperMario extends JPanel implements Runnable {

    private boolean hasTakeMashroom;
    private boolean goRight;
    private boolean goLeft;
    private boolean isJumping;
    public int intexWalkingMario = 1;
    private Image imageMarioWalk = getToolkit().getImage("1.png");
    private Point startingPoint = new Point(150, 100);
    private MarioPanel panel;

    //private static int colum = 300;
    public SuperMario(MarioPanel panel) {
        this.panel = panel;
        setSize(60, 70);

        setLocation(startingPoint);
    }

    /**
     * ani pidaei emfanizee ayti i eikona(kaleite i methodos kai meta i repaint)
     */
    public void marioJump(Graphics g) {

        imageMarioWalk = getToolkit().getImage("jump.png");
        g.drawImage(imageMarioWalk, 0, 0, this);

    }

    /**
     *
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (isJumping) {
                setLocation(getLocation().x, getLocation().y - 1);
                for (int i = 0; i < MarioPanel.gifts.size(); i++) {// gia na min mporei na mpei apo katw mesa sto dwro
                    if (MarioPanel.gifts.get(i).getRectangle2().intersects(getRectangle())) {//getRectangle().x, getRectangle().y - 1, getRectangle().width, getRectangle().height
                        this.setIsJumping(false);
                    }
                }

            }





            if (isGoRight()) {// an pigenei deksia
                intexWalkingMario++;
                if (intexWalkingMario > 3) {
                    intexWalkingMario = 1;
                }
                // if (chechForRightAnimation()) {
                for (int i = 0; i < MarioPanel.world.size(); i++) {// metatopizw to edafos  // stage

                    if (chechForRightAnimation()) {//metakinei oli tin skini an ptisw to deksia pliktro pros ta deksia
                        MarioPanel.world.get(i).setLocation(((JPanel) MarioPanel.world.get(i)).getLocation().x - 1, ((JPanel) MarioPanel.world.get(i)).getLocation().y);


                    }
                }


//                for (int i = 0; i < MarioPanel.gifts.size(); i++) {//metatopizw ta dwra
//                    if (chechForRightAnimation()) {
//                        MarioPanel.gifts.get(i).setLocation(MarioPanel.gifts.get(i).getLocation().x - 1, MarioPanel.gifts.get(i).getLocation().y);
//                    }
//                }
//                for (int i = 0; i < MarioPanel.mashrooms.size(); i++) {//metatopizw ta μανιταρια
//                    if (chechForRightAnimation()) {
//                        MarioPanel.mashrooms.get(i).setLocation(MarioPanel.mashrooms.get(i).getLocation().x - 1, MarioPanel.mashrooms.get(i).getLocation().y);
//                    }
//                }
//                for (int i = 0; i < MarioPanel.enemies.size(); i++) {//metatopizw tous exthrous
//                    if (chechForRightAnimation()) {
//                        MarioPanel.enemies.get(i).setLocation(MarioPanel.enemies.get(i).getLocation().x - 1, MarioPanel.enemies.get(i).getLocation().y);
//                    }
//                }

            }



            if (isGoLeft()) {// metatopizw tin skini oli pros ta aristera an den exei kapoio empodio mprosta o mario
                intexWalkingMario--;
                if (intexWalkingMario < -3) {
                    intexWalkingMario = -1;
                }

                for (int i = 0; i < MarioPanel.world.size(); i++) {// metatopizw to edafos // stage
                    if (chechForLeftAnimation()) {
                        MarioPanel.world.get(i).setLocation(((JPanel) MarioPanel.world.get(i)).getLocation().x + 1, ((JPanel) MarioPanel.world.get(i)).getLocation().y);
                    }
                }
//                for (int i = 0; i < MarioPanel.gifts.size(); i++) {//metatopizw ta dwra
//                    if (chechForLeftAnimation()) {
//                        MarioPanel.gifts.get(i).setLocation(MarioPanel.gifts.get(i).getLocation().x + 1, MarioPanel.gifts.get(i).getLocation().y);
//                    }
//                }
//                for (int i = 0; i < MarioPanel.mashrooms.size(); i++) {//metatopizw ta dwra
//                    if (chechForLeftAnimation()) {
//                        MarioPanel.mashrooms.get(i).setLocation(MarioPanel.mashrooms.get(i).getLocation().x + 1, MarioPanel.mashrooms.get(i).getLocation().y);
//                    }
//                }
//                for (int i = 0; i < MarioPanel.enemies.size(); i++) {//metatopizw ta dwra
//                    if (chechForLeftAnimation()) {
//                        MarioPanel.enemies.get(i).setLocation(MarioPanel.enemies.get(i).getLocation().x + 1, MarioPanel.enemies.get(i).getLocation().y);
//                    }
//                }

            }


            boolean isWalkingOnSomething = true; // ama pataei kapou an oxi tote katevenei pros t katw


            for (int i = 0; i < MarioPanel.stage.size(); i++) {
                if (MarioPanel.stage.get(i).getRectangle().intersects(getRectangle()) || isJumping) {
                    isWalkingOnSomething = false;
                }
            }
            for (int i = 0; i < MarioPanel.gifts.size(); i++) {
                if (MarioPanel.gifts.get(i).getRectangle().intersects(getRectangle()) || isJumping) {
                    isWalkingOnSomething = false;
                }
            }



            if (isWalkingOnSomething) {
                setLocation(getLocation().x, getLocation().y + 1);
            }

            if ((getLocation().y + getHeight()) > MFrame.height) {
                looseLife();
            }



            repaint();
        }


    }

    /**
     *
     */
    public void looseLife() {
       
        MarioPanel.lives--;
        if (MarioPanel.lives > 0) {
            setLocation(startingPoint);
            for (CreateStageObj stageObj : MarioPanel.stage) {
                stageObj.startingPoint();
            }
            for (Gift gift : MarioPanel.gifts) {
                gift.startingPoint();
            }
            for (Enemy enemy : MarioPanel.enemies) {
                enemy.startingPoint();

            }
            for (Mashroom m:MarioPanel.mashrooms) {
                panel.remove(m);
            }
            setIsJumping(false);
            this.setHasTakeMashroom(false);
            
        } else {
            panel.removeAll();
        }



//            if (!enemy.getThread().isAlive()) {
//              enemy.setThread(new Thread(enemy));
//              enemy.getThread().start();
//            }
        //    enemy.startThread(); to ensomatwsa stin startpoint

        panel.repaint();


    }

    /**
     * gia tyxon provlimta mporoume na dimiourgisoume paromoies methodous gia
     * tin epilisi tous
     */
    private void sequrityUpdate() {
        for (int i = 0; i < MarioPanel.gifts.size(); i++) {
            if (MarioPanel.gifts.get(i).getRectangle().contains(getRectangle().x + 2, getRectangle().y + 2, getRectangle().getWidth() - 2, getRectangle().getHeight() - 2)) {
                // gia logous asfaleis , min kolisei ( min tyxon mpei mesa se ena dwro i object )
                setLocation(getLocation().x, getLocation().y + 1);

            }
        }
    }

    /**
     *
     * elengxw an yparxei embodio pros ta deksia gia na metatopisw tin skini mou
     * pros ta ekei
     *
     * @return
     */
    private boolean chechForRightAnimation() {
        for (int i = 0; i < MarioPanel.stage.size(); i++) {
            if ((MarioPanel.stage.get(i).getRectangle().intersects(getRightRectangle()))) {
                return false;
            }
        }
        for (int i = 0; i < MarioPanel.gifts.size(); i++) {
            if ((MarioPanel.gifts.get(i).getRectangle().intersects(getRightRectangle()))) {
                return false;
            }
        }


        return true;
    }

    /**
     * metakinei oli tin skini an ptisw to aristero pliktro pros ta aristera
     *
     * @return
     */
    private boolean chechForLeftAnimation() {
        for (int i = 0; i < MarioPanel.stage.size(); i++) {
            if ((MarioPanel.stage.get(i).getRectangle().intersects(this.getLeftRectangle()))) {
                return false;
            }
        }
        for (int i = 0; i < MarioPanel.gifts.size(); i++) {
            if ((MarioPanel.gifts.get(i).getRectangle().intersects(getLeftRectangle()))) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @return
     */
    public boolean isGoLeft() {
        return goLeft;
    }

    /**
     *
     * @param goLeft
     */
    public void setGoLeft(boolean goLeft) {
        this.goLeft = goLeft;
    }

    /**
     *
     * @return
     */
    public boolean isGoRight() {
        return goRight;
    }

    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }

    /**
     *
     * @return
     */
    public boolean isIsJumping() {
        return isJumping;
    }

    /**
     *
     * @return
     */
    public Rectangle getRectangle() {

        return new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight());
    }

    /**
     * to xrysimopoiw gia na min mpenw mesa se toixous
     *
     * @return
     */
    public Rectangle getRightRectangle() {

        return new Rectangle(getLocation().x + 4, getLocation().y - 1, getWidth() + 1, getHeight());
    }

    public Rectangle getLeftRectangle() {

        return new Rectangle(getLocation().x - 1, getLocation().y - 1, getWidth() - 4, getHeight());
    }

    /**
     *
     * @param isJumping
     */
    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public Rectangle getJumpRectangle() {

        return new Rectangle(getRectangle().x + getWidth() / 4 - 2, getRectangle().y - 3, getRectangle().width - +getWidth() / 3 - 2, getRectangle().height - 3);
    }

    public int getIntexWalkingMario() {
        return intexWalkingMario;
    }

    public void setIntexWalkingMario(int intexWalkingMario) {
        this.intexWalkingMario = intexWalkingMario;
    }

    public boolean isHasTakeMashroom() {
        return hasTakeMashroom;
    }

    public void setHasTakeMashroom(boolean hasTakeMashroom) {
        this.hasTakeMashroom = hasTakeMashroom;
    }

    /**
     *
     * @param g
     */
    public void marioAnimation(Graphics g) {
        int posx = 0, posy = 0;
        if (hasTakeMashroom) {
            setSize(50, 65);
        } else {
            setSize(30, 43);
        }
        if (!isJumping) {
            if (intexWalkingMario == 1 && isHasTakeMashroom()) {
                imageMarioWalk = getToolkit().getImage("1.png");
                posx = -10;
                posy = -10;
            } else if (intexWalkingMario == 1) {
                imageMarioWalk = getToolkit().getImage("1Small.png");
                posx = -10;
                posy = -7;

            }
            if (intexWalkingMario == 2 && isHasTakeMashroom()) {
                imageMarioWalk = getToolkit().getImage("2.png");
                posx = -50;
                posy = -10;
            } else if (intexWalkingMario == 2) {
                imageMarioWalk = getToolkit().getImage("2Small.png");
                posx = -45;
                posy = -7;
            }

            if (intexWalkingMario == 3 && isHasTakeMashroom()) {
                imageMarioWalk = getToolkit().getImage("3.png");
                posx = -90;
                posy = -10;
            } else if (intexWalkingMario == 3) {
                imageMarioWalk = getToolkit().getImage("3Small.png");
                posx = -70;
                posy = -7;
            }

            if (intexWalkingMario == -1 && isHasTakeMashroom()) {
                imageMarioWalk = getToolkit().getImage("1Back.png");
                posx = -95;
                posy = -10;
            } else if (intexWalkingMario == -1) {
                imageMarioWalk = getToolkit().getImage("1BackSmall.png");
                posx = -60;
                posy = -7;
            }
            if (intexWalkingMario == -2 && isHasTakeMashroom()) {
                imageMarioWalk = getToolkit().getImage("2Back.png");
                posx = -50;
                posy = -10;
            } else if (intexWalkingMario == -2) {
                imageMarioWalk = getToolkit().getImage("2BackSmall.png");
                posx = -40;
                posy = -7;
            }


            if (intexWalkingMario == -3 && isHasTakeMashroom()) {
                imageMarioWalk = getToolkit().getImage("3Back.png");
                posx = -10;
                posy = -10;
            } else if (intexWalkingMario == -3) {
                imageMarioWalk = getToolkit().getImage("3BackSmall.png");
                posx = 0;
                posy = -7;
            }
            System.out.println(intexWalkingMario);
        } else {

            if (goLeft && isHasTakeMashroom()) {
                setSize(60, 70);
                imageMarioWalk = getToolkit().getImage("jumpLeft.png");
                posy = -120;
                posx = -43;
            } else if (goLeft) {
                imageMarioWalk = getToolkit().getImage("jumpLeftSmall.png");
                posy = -85;
                posx = -40;

            } else if (isHasTakeMashroom()) {
                setSize(60, 70);
                imageMarioWalk = getToolkit().getImage("jumpRight.png");
                posy = -120;
                posx = -43;
            } else {
                imageMarioWalk = getToolkit().getImage("jumpRightSmall.png");
                posy = -85;
                posx = -40;

            }
        }

        //  System.out.println(intexWalkingMario);
        g.drawImage(imageMarioWalk, posx, posy, this);

    }
    /*
     * 
     */

    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        marioAnimation(g);

    }
}
