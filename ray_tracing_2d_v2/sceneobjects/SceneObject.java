package ray_tracing_2d_v2.sceneobjects;

import java.awt.*;

public abstract class SceneObject
{
    private int x;
    private int y;
    private final ID id;

    public SceneObject(int x, int y, ID id)
    {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public ID getId()
    {
        return id;
    }

    public abstract void tick();
    public abstract void render(Graphics2D g2d);
}