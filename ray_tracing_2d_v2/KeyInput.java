package ray_tracing_2d_v2;

import ray_tracing_2d_v2.sceneobjects.Handler;
import ray_tracing_2d_v2.sceneobjects.ID;
import ray_tracing_2d_v2.sceneobjects.Sprite;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter
{
    private final Handler handler;
    private boolean isW = false;
    private boolean isA = false;
    private boolean isS = false;
    private boolean isD = false;
    private boolean isR = false;
    private boolean isL = false;

    public KeyInput(Handler handler)
    {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W) isW = true;
        if(key == KeyEvent.VK_A) isA = true;
        if(key == KeyEvent.VK_S) isS = true;
        if(key == KeyEvent.VK_D) isD = true;
        if(key == KeyEvent.VK_RIGHT) isR = true;
        if(key == KeyEvent.VK_LEFT) isL = true;
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W) isW = false;
        if(key == KeyEvent.VK_A) isA = false;
        if(key == KeyEvent.VK_S) isS = false;
        if(key == KeyEvent.VK_D) isD = false;
        if(key == KeyEvent.VK_RIGHT) isR = false;
        if(key == KeyEvent.VK_LEFT) isL = false;
    }

    public void tick()
    {
        for(Sprite sprite : handler.sprites)
        {
            if(sprite.getId() == ID.Player)
            {
                if(isW && !isA && !isS && !isD) sprite.setVelY(-5);
                if(!isW && isA && !isS && !isD) sprite.setVelX(-5);
                if(!isW && !isA && isS && !isD) sprite.setVelY(5);
                if(!isW && !isA && !isS && isD) sprite.setVelX(5);
                if(isW && isA && !isS && !isD)
                {
                    sprite.setVelX(-5);
                    sprite.setVelY(-5);
                }
                if(isW && !isA && !isS && isD)
                {
                    sprite.setVelX(5);
                    sprite.setVelY(-5);
                }
                if(!isW && isA && isS && !isD)
                {
                    sprite.setVelX(-5);
                    sprite.setVelY(5);
                }
                if(!isW && !isA && isS && isD)
                {
                    sprite.setVelX(5);
                    sprite.setVelY(5);
                }
                if(!isW && !isA && !isS && !isD)
                {
                    sprite.setVelX(0);
                    sprite.setVelY(0);
                }
                if(isR)
                {
                    sprite.setAngle(sprite.getAngle() + 0.02);
                }
                if(isL)
                {
                    sprite.setAngle(sprite.getAngle() - 0.02);
                }
                break;
            }
        }
    }
}