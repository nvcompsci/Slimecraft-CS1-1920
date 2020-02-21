/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slimecraft;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

/**
 *
 * @author jword
 */
public class World extends JPanel implements MouseListener {
    private ArrayList<Blob> blobs = new ArrayList<>();    
    private ArrayList<Glob> globs = new ArrayList<>();    
    private ArrayList<Sprite> sprites = new ArrayList<>();
    private ArrayList<Food> foods = new ArrayList<>();   
    private ArrayList<Slime> slimes = new ArrayList<>();    
    Timer timer;
    int frames = 0;
    KeyEvent lastPressed;
    
    public World() {
        this.addMouseListener(this);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 100, 1000/12);
        super.setSize(800, 600);
        for (int i = 0; i < 80; i++) {
            int x = (int) (Math.random() * 800 / 2);
            int y = (int) (Math.random() * 600);
            Blob blob = new Blob(x,y);
            sprites.add(blob);
            slimes.add(blob);
            blobs.add(blob);
            System.out.println(blob);
        }
        for (int i = 0; i < 80; i++) {
            int x = (int) (Math.random() * 800 / 2 + 800 / 2);
            int y = (int) (Math.random() * 600);
            Glob glob = new Glob(x,y);
            sprites.add(glob);    
            slimes.add(glob);    
            globs.add(glob);    
        }
        for (int i = 0; i < 75; i++) {
            int x = (int) (Math.random() * 800);
            int y = (int) (Math.random() * 600);
            Food food = new Food(x,y);
            foods.add(food);
            sprites.add(food);
        }  
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        frames++;
        
        if (frames == 36) {
            g.drawString("Hi!",200,200);
        }
        
        for (Slime slime : slimes) {
            for (Food food : foods) {
                slime.eat(food);
            }            
        }
        
        ArrayList<Blob> newBlobs = new ArrayList<>();
        ArrayList<Glob> newGlobs = new ArrayList<>();
        
        for (Blob blob : blobs) {
            for (Glob glob : globs) {
                blob.fight(glob);
            }
            for (Blob otherBlob : blobs) {
                if (blob == otherBlob) continue;
                if (blob.collide(otherBlob) && Math.random() < 0.05) {
                    //newBlobs.add(blob.reproduce(otherBlob));
                }
            }
        }
        for (Glob glob : globs) {
            for (Glob otherGlob : globs) {
                if (glob == otherGlob) continue;
                if (glob.collide(otherGlob) && Math.random() < 0.05) {
                    //newGlobs.add(glob.reproduce(otherGlob));
                }
            }
        }
        for (Sprite sprite : sprites) {
            sprite.draw(g);
            sprite.update();
            sprite.collideWorldBounds(800,600);
        }        
        
        takeOutTheTrash();
        addNewSlimes(newBlobs, newGlobs);
    }
    
    private void addNewSlimes(ArrayList<Blob> newBlobs, ArrayList<Glob> newGlobs) {
        blobs.addAll(newBlobs);
        globs.addAll(newGlobs);
        sprites.addAll(newBlobs);
        sprites.addAll(newGlobs);
    }
    
    private void takeOutTheTrash() {
        ArrayList<Sprite> trash = new ArrayList<>();
        for (Sprite sprite : sprites) {
            if (!sprite.isAlive())
                trash.add(sprite);
        }
        sprites.removeAll(trash);
        trash.clear();
        for (Food food : foods) {
            if (!food.isAlive())
                trash.add(food);
        }
        foods.removeAll(trash);
        trash.clear();
        for (Blob blob : blobs) {
            if (!blob.isAlive())
                trash.add(blob);
        }
        blobs.removeAll(trash);
        trash.clear();
        for (Glob glob : globs) {
            if (!glob.isAlive())
                trash.add(glob);
        }
        globs.removeAll(trash);
        trash.clear();        
    }
    
    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            repaint();
        }
    }
    
    public void keyPressed(KeyEvent event) {
        lastPressed = event;
        
    }
    
    @Override
    public void mouseExited(MouseEvent event) {
        
    }
    @Override
    public void mouseEntered(MouseEvent event) {
        
    }
    @Override
    public void mousePressed(MouseEvent event) {
        
    }
    @Override
    public void mouseReleased(MouseEvent event) {
        
    }
    @Override
    public void mouseClicked(MouseEvent event) {
        System.out.printf("\nMouse Click at (%d, %d)",event.getX(), event.getY());
        
        switch (lastPressed.getKeyCode()) {
            case KeyEvent.VK_G :
                System.out.println("G");
                Glob glob = new Glob(event.getX(), event.getY());
                globs.add(glob);
                slimes.add(glob);
                sprites.add(glob);
                break;
            case KeyEvent.VK_B :
                System.out.println("B");
                Blob blob = new Blob(event.getX(), event.getY());
                blobs.add(blob);
                slimes.add(blob);
                sprites.add(blob);
                break;
            case KeyEvent.VK_F :
                System.out.println("F");
                Food food = new Food(event.getX(), event.getY());
                foods.add(food);
                sprites.add(food);
                break;
            case KeyEvent.VK_V :
                System.out.println("V");
                break;
        }
        
    }
}
