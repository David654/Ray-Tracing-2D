package ray_tracing_2d_v2;

import ray_tracing_2d_v2.sceneobjects.Handler;
import ray_tracing_2d_v2.sceneobjects.ID;
import ray_tracing_2d_v2.sceneobjects.Sprite;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter
{
    private final Handler handler;
    private int selectedIndex = -1;

    public MouseInput(Handler handler)
    {
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e)
    {
        int mx = e.getX();
        int my = e.getY();

        for(int i = 0; i < handler.sprites.size(); i++)
        {
            Sprite sprite = handler.sprites.get(i);
            if(sprite.getMaterial() == ID.LightSource && mouseOver(mx, my, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight()))
            {
                selectedIndex = i;
                break;
            }
        }
    }

    public void mouseDragged(MouseEvent e)
    {
        int mx = e.getX();
        int my = e.getY();

        for(int i = 0; i < handler.sprites.size(); i++)
        {
            Sprite sprite = handler.sprites.get(i);
            if(i == selectedIndex)
            {
                sprite.setX(mx);
                sprite.setY(my);
                break;
            }
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height)
    {
        if(mx > x && mx < x + width)
        {
            return my > y && my < y + height;
        }
        else
        {
            return false;
        }
    }
}