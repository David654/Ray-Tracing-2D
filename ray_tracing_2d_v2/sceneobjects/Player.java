package ray_tracing_2d_v2.sceneobjects;

import ray_tracing_2d_v2.Data;

import java.awt.*;

public class Player extends Sprite
{
    private final Handler handler;
    private int lastX, lastY;

    public Player(int x, int y, int width, int height, ID id, Color color, ID material, Handler handler)
    {
        super(x, y, width, height, id, color, material);
        setVelX(0);
        setVelY(0);
        setAngle(0);
        this.handler = handler;
    }

    public void tick()
    {
        lastX = getX();
        lastY = getY();

        setX(getX() + getVelX());
        setY(getY() + getVelY());

        collision();
    }

    private void collision()
    {
        for(int i = 0; i < handler.sprites.size(); i++)
        {
            Sprite sprite = handler.sprites.get(i);
            if(sprite.getId() == ID.Wall)
            {
                if(this.getBounds().intersects(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight()))
                {
                    setX(lastX);
                    setY(lastY);
                    setVelX(0);
                    setVelY(0);
                    break;
                }
            }
        }
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(Color.white);
        int x = getX() + getWidth() / 2;
        int y = getY() + getHeight() / 2;
        g2d.drawLine(x, y, (int) (x + Data.tile * Math.cos(getAngle())), (int) (y + Data.tile * Math.sin(getAngle())));

        g2d.setColor(getColor());
        g2d.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}