package ray_tracing_2d_v2.sceneobjects;

import java.awt.*;

public class Wall extends Sprite
{
    public Wall(int x, int y, int width, int height, ID id, Color color, ID material)
    {
        super(x, y, width, height, id, color, material);
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(getColor());
        //g2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}