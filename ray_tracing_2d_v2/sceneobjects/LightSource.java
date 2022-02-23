package ray_tracing_2d_v2.sceneobjects;

import java.awt.*;

public class LightSource extends Sprite
{
    public LightSource(int x, int y, int width, int height, ID id, Color color, ID material)
    {
        super(x, y, width, height, id, color, material);
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(getColor());
        g2d.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}