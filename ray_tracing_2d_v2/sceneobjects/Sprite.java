package ray_tracing_2d_v2.sceneobjects;

import java.awt.*;

public abstract class Sprite extends SceneObject
{
    private final int width;
    private final int height;
    private int velX;
    private int velY;
    private double angle;
    private final Color color;
    private final ID material;

    public Sprite(int x, int y, int width, int height, ID id, Color color, ID material)
    {
        super(x, y, id);
        this.width = width;
        this.height = height;
        this.color = color;
        this.material = material;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getVelX()
    {
        return velX;
    }

    public void setVelX(int velX)
    {
        this.velX = velX;
    }

    public int getVelY()
    {
        return velY;
    }

    public void setVelY(int velY)
    {
        this.velY = velY;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public Color getColor() {
        return color;
    }

    public ID getMaterial() {
        return material;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(getX(), getY(), width, height);
    }
}