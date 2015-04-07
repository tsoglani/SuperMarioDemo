/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import javax.swing.JFrame;

/**
 *
 * @author gaitanesnikos
 */
public class MFrame extends JFrame {

    private MarioPanel p;
    static boolean canJump = true;
    boolean firstJump = true;
    public static int width = 1100;
    public static int height = 700;

    public MFrame() {
        this.setSize(width, height);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        p = new MarioPanel();
        this.add(p, BorderLayout.CENTER);
        this.setVisible(true);

    }

    public void paint(Graphics g) {
        super.paint(g);
        this.setSize(1100, 700);
    }
}
