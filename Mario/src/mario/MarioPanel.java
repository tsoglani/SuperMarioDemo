package mario;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

/**
 *
 * @author gaitanesnikos
 */
public class MarioPanel extends JPanel {

    private KeyListener key;
    private SuperMario mario;
    private boolean gameIsOver = false;
    static ArrayList<CreateStageObj> stage = new ArrayList<CreateStageObj>();
    static ArrayList<Gift> gifts = new ArrayList<Gift>();
    static ArrayList<Mashroom> mashrooms = new ArrayList<Mashroom>();
    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    static ArrayList<Rec> world = new ArrayList<Rec>();
    static int lives = 3;
    private Image hearts = getToolkit().getImage("heart.png");

    public MarioPanel() {
        mario = new SuperMario(this);
        new Thread(mario).start();
        add(mario);
        setLayout(null);

        createListener();
        generateWorld();
        setFocusable(true);
        addKeyListener(key);
        repaint();
    }

    /**
     *
     */
    private void generateWorld() {
        generateLevel(new CreateStageObj(0, 570, 920, 610, Color.darkGray));
        generateLevel(new CreateStageObj(920, 500, 130, 610, Color.green));
        generateGift(300, 450);
        generateEnemy(500, 400);

        //generateGifts(250, 500);
    }

    /*
     * 
     */
    public void createListener() {

        key = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (KeyEvent.VK_RIGHT == e.getKeyCode()) {
                    mario.setIntexWalkingMario(1);
                    mario.setGoRight(true);
                } else if (KeyEvent.VK_LEFT == e.getKeyCode()) {

                    mario.setGoLeft(true);
                    mario.setIntexWalkingMario(-1);
                } else if (KeyEvent.VK_UP == e.getKeyCode() || KeyEvent.VK_SPACE == e.getKeyCode()) {
                    boolean canJump = false;
                    for (int i = 0; i < MarioPanel.stage.size(); i++) {
                        if (MarioPanel.stage.get(i).getRectangle().intersects(mario.getRectangle())) {
                            canJump = true;
                        }
                    }
                    for (int i = 0; i < MarioPanel.gifts.size(); i++) {
                        if (MarioPanel.gifts.get(i).getRectangle().intersects(mario.getRectangle())) {
                            canJump = true;
                        }
                    }

                    if (canJump) {
                        mario.setIsJumping(true);
                        mario.repaint();
                        new Thread() {
                            public void run() {
                                try {
                                    Thread.sleep(300);
                                } catch (Exception e) {
                                }
                                mario.setIsJumping(false);
                            }
                        }.start();

                    }
                }





                mario.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (KeyEvent.VK_RIGHT == e.getKeyCode()) {
                    mario.setGoRight(false);
                }
                if (KeyEvent.VK_LEFT == e.getKeyCode()) {
                    mario.setGoLeft(false);
                }
            }
        };
    }

    /**
     *
     * @param r
     */
    public void generateLevel(CreateStageObj r) {
        stage.add(r);
        add(r);
        MarioPanel.addIntoWorld(r);
    }

    /**
     *
     * @param gift
     */
    public void generateGift(int posx, int posy) {
        Gift gift = new Gift(posx, posy, this);
        gift.startThread();
        gifts.add(gift);
        add(gift);
        MarioPanel.addIntoWorld(gift);
    }

    /**
     *
     * @param posx
     * @param posy
     */
    public void generateEnemy(int posx, int posy) {

        Enemy enemy = new Enemy(posx, posy, this);
        add(enemy);
        enemy.startingPoint();
        enemies.add(enemy);
        MarioPanel.addIntoWorld(enemy);
    }

    /**
     *
     * @param gr
     */
    public void paintComponent(Graphics gr) {

        Graphics2D g = (Graphics2D) gr;

        super.paintComponent(g);
        if (lives <= 0) {
            gameIsOver = true;

        }

        if (!gameIsOver) {
            g.setColor(Color.orange);
            for (int i = 0; i < lives; i++) {
                g.drawImage(hearts, (i + 1) * 50, 0, null, this);

            }

        } else {

            g.clearRect(0, 0, 1100, 1100);
            Font f = new Font("", Font.BOLD, 100);
            g.setFont(f);
            g.drawString("GameOver ", 100, 100);

        }


////////        for (Enemy en:MarioPanel.enemies) {
////////            g.fill(en.getWeekRectangle());
////////        }
//        g.setColor(Color.red);
//        g.fill(mario.getJumpRectangle());
//        g.setColor(Color.blue);
//        g.fill(MarioPanel.gifts.get(0).getRectangle2());


    }

    public static void addIntoWorld(Rec r) {
        world.add(r);

    }

    public SuperMario getMario() {
        return mario;
    }

    public void setMario(SuperMario mario) {
        this.mario = mario;
    }
}
