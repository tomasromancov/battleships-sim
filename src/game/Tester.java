package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

// GCTester demonstrates how we can override the GameCore class
// to create our own 'game'. We usually need to implement at
// least 'draw' and 'update' (not including any local event handling)
// to begin the process. You should also write your own 'init'
// method that will initialise event handlers etc. By default GameCore
// will handle the 'Escape' key to quit the game but you can
// override this with your own event handler.

public class Tester extends GameCore{

	Boolean paused = false;
	TileMap tmap = new TileMap(); 
	
	boolean debug = false;
    long total;         // Total time elapsed
    private double angle = 100;
 
    
    // Initialise the class, e.g. set up variables
    // animations, register event handlers
    public void init(char[][] grid)
    {

        tmap.loadMap(grid); 
        
    }

    // Draw the current frame
    public void draw(Graphics2D g, char[][] grid)
    {
        // A simple demo - note that this is not 
        // very efficient since we fill the screen
        // on every frame.
//        g.setColor(Color.black);
//        g.fillRect(0,0,getWidth(),getHeight());
//        g.setColor(Color.yellow);
//        g.drawString("Time Expired:" + total,30,50);
        
    	tmap.loadMap(grid);
        tmap.draw(g,0,0); 
        System.out.println("map was drawn");
        
//        if(debug) {
//        	g.setColor(Color.yellow); 
//        }
        
        
    }

    
    public void keyPressed(KeyEvent e)  
    { 
       switch (e.getKeyCode()) 
          { 
             case KeyEvent.VK_ESCAPE: stop(); break; // Stop game loop 
             //case KeyEvent.VK_S: rock.stop(); break; // Stop the rock 
             //case KeyEvent.VK_Q: rock.setVelocity(0.1f, 0.1f); //resume the rock's motion
             case KeyEvent.VK_P: paused = !paused;
             case KeyEvent.VK_RIGHT: angle = (angle + 20) % 360; break; 
             case KeyEvent.VK_LEFT: angle = (angle - 20) % 360; break; 
             
             case KeyEvent.VK_F5: debug = !debug; break;
             default: break; // Unused key event 
       } 
       e.consume(); 
    } 
}
