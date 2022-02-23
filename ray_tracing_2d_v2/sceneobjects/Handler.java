package ray_tracing_2d_v2.sceneobjects;

import java.awt.*;
import java.util.ArrayList;

public class Handler
{
    public ArrayList<SceneObject> sceneObjects;
    public ArrayList<Sprite> sprites;

    public Handler()
    {
        sceneObjects = new ArrayList<>();
        sprites = new ArrayList<>();
    }

    public void addObject(SceneObject object)
    {
        sceneObjects.add(object);
    }

    public void addSprite(Sprite sprite)
    {
        sprites.add(sprite);
    }

    public void tick()
    {
        for(int i = 0; i < sceneObjects.size(); i++)
        {
            SceneObject object = sceneObjects.get(i);
            object.tick();
        }

        for(int i = 0; i < sprites.size(); i++)
        {
            Sprite sprite = sprites.get(i);
            sprite.tick();
        }
    }

    public void render(Graphics2D g2d)
    {
        for(int i = 0; i < sceneObjects.size(); i++)
        {
            SceneObject object = sceneObjects.get(i);
            object.render(g2d);
        }

        for(int i = 0; i < sprites.size(); i++)
        {
            Sprite sprite = sprites.get(i);
            sprite.render(g2d);
        }
    }
}