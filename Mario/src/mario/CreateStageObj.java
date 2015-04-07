/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author gaitanesnikos
 */
public class CreateStageObj extends JPanel implements Rec {

    private int width, height;
    private int posX, posY;
    private Color clr;
    private Rectangle rectangle;

    public CreateStageObj(int posX, int posY, int width, int height, Color clr) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.clr = clr;
        setSize(width, height);
        setLocation(posX, posY);
        rectangle = new Rectangle(posX, posY, width, height);
    }

    public void startingPoint() {
        setLocation(posX, posY);

    }

    public Rectangle getRectangle() {
        rectangle = new Rectangle(getLocation().x, getLocation().y, width, height);
        return rectangle;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(clr);
        g.fillRect(0, 0, width, height);
    }
}
