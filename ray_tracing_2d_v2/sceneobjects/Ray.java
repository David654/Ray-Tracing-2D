package ray_tracing_2d_v2.sceneobjects;

import ray_tracing_2d_v2.Data;
import ray_tracing_2d_v2.Scene;

import java.awt.*;
import java.awt.geom.Line2D;

public class Ray extends SceneObject
{
    private final Scene scene;
    private final Sprite lightSource;
    private final int startX, startY;
    private int x1, y1, x2, y2;
    private double angle;
    private double dist;
    private double curr_angle;

    private int side = 0;
    public boolean intersects = false;

    private Color color = new Color(255, 255, 255);
    private int a = 1;

    public Ray(Scene scene, int x, int y, double angle, Sprite lightSource)
    {
        super(x, y, ID.Ray);
        this.scene = scene;
        this.angle = angle;
        this.lightSource = lightSource;

        startX = x;
        startY = y;

        x1 = x;
        y1 = y;

        curr_angle = lightSource.getAngle() - Data.fov / 2 + angle;
    }

    public int getX2()
    {
        return x2;
    }

    public int getY2()
    {
        return y2;
    }

    public double getCurrAngle()
    {
        return curr_angle;
    }

    public double getDist()
    {
        return dist;
    }

    public int getSide()
    {
        return side;
    }

    public void tick()
    {

    }

    private int[] intersection(Line2D a, Line2D b)
    {
        double x1 = a.getX1(), y1 = a.getY1(), x2 = a.getX2(), y2 = a.getY2(), x3 = b.getX1(), y3 = b.getY1(), x4 = b.getX2(), y4 = b.getY2();
        double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if(d == 0)
        {
            return new int[] {Data.width * 2, Data.height * 2};
        }

        double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
        double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

        return new int[] {(int) xi, (int) yi};
    }

    private double[] calculate(int x1, int y1, int x2, int y2)
    {
        for(int i = 0; i < scene.handler.sprites.size(); i++)
        {
            Sprite sprite = scene.handler.sprites.get(i);
            if(sprite.getId() == ID.Wall)
            {
                double x = sprite.getX();
                double y = sprite.getY();
                double w = sprite.getWidth();
                double h = sprite.getHeight();

                Line2D l0 = new Line2D.Double(x1, y1, x2, y2);
                Line2D l1 = new Line2D.Double(x, y, x + w, y);
                Line2D l2 = new Line2D.Double(x + w, y, x + w, y + h);
                Line2D l3 = new Line2D.Double(x, y + h, x + w, y + h);
                Line2D l4 = new Line2D.Double(x, y, x, y + h);

                int[] intersection;
                int ix, iy;

                if(y1 > l3.getY1())
                {
                    if(l0.intersectsLine(l3))
                    {
                        intersection = intersection(l0, l3);
                        ix = intersection[0];
                        iy = intersection[1];
                        dist = Math.sqrt(Math.pow(ix - x1, 2) + Math.pow(iy - y1, 2));
                        x2 = ix;
                        y2 = iy;
                        side = 3;
                        color = sprite.getColor();
                        intersects = true;
                    }
                }
                else if(y1 < l1.getY1())
                {
                    if(l0.intersectsLine(l1))
                    {
                        intersection = intersection(l0, l1);
                        ix = intersection[0];
                        iy = intersection[1];
                        dist = Math.sqrt(Math.pow(ix - x1, 2) + Math.pow(iy - y1, 2));
                        x2 = ix;
                        y2 = iy;
                        side = 1;
                        color = sprite.getColor();
                        intersects = true;
                    }
                }

                if(x1 > l2.getX1())
                {
                    if(l0.intersectsLine(l2))
                    {
                        intersection = intersection(l0, l2);
                        ix = intersection[0];
                        iy = intersection[1];
                        dist = Math.sqrt(Math.pow(ix - x1, 2) + Math.pow(iy - y1, 2));
                        x2 = ix;
                        y2 = iy;
                        side = 2;
                        color = sprite.getColor();
                        intersects = true;
                    }
                }
                else if(x1 < l4.getX1())
                {
                    if(l0.intersectsLine(l4))
                    {
                        intersection = intersection(l0, l4);
                        ix = intersection[0];
                        iy = intersection[1];
                        dist = Math.sqrt(Math.pow(ix - x1, 2) + Math.pow(iy - y1, 2));
                        x2 = ix;
                        y2 = iy;
                        side = 4;
                        color = sprite.getColor();
                        intersects = true;
                    }
                }
            }
        }
        return new double[] {x2, y2, side, dist};
    }

    private boolean intersectsLightSource(Line2D ray)
    {
        for(int i = 0; i < scene.handler.sprites.size(); i++)
        {
            Sprite sprite = scene.handler.sprites.get(i);
            if(sprite.getMaterial() == ID.LightSource)
            {
                if(ray.intersects(sprite.getBounds()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void render(Graphics2D g2d)
    {
        double curr_angle = lightSource.getAngle() - angle;
        x1 = lightSource.getX() + Data.tile / 2;
        y1 = lightSource.getY() + Data.tile / 2;
        x2 = (int) (x1 + Data.maxDepth * Math.cos(curr_angle));
        y2 = (int) (y1 + Data.maxDepth * Math.sin(curr_angle));

        Rectangle rect = new Rectangle(0, 0, Data.width, Data.height);

        Color rayColor = lightSource.getColor();
        double a = 1.0;

        int n = 10;

        for(int i = 0; i < Data.maxIter; i++)
        {
            double[] c = calculate(x1, y1, x2, y2);
            x2 = (int) c[0];
            y2 = (int) c[1];
            int side = (int) c[2];
            double dist = c[3];

            if(i == 0)
            {
                g2d.setColor(rayColor);
                //g2d.drawLine(x1, y1, x2, y2);
            }

            g2d.setColor(rayColor);
            //g2d.drawLine(x1, y1, x2, y2);



            if(rect.contains(x2, y2))
            {
                scene.image.setRGB(x2, y2, rayColor.getRGB());

                x1 = x2;
                y1 = y2;

                if(side == 1 || side == 3)
                {
                    x2 = (int) (x1 + Data.maxDepth * Math.cos(-curr_angle));
                    y2 = (int) (y1 + Data.maxDepth * Math.sin(-curr_angle));
                }
                else
                {
                    x2 = (int) (x1 + Data.maxDepth * Math.cos(Math.PI - curr_angle));
                    y2 = (int) (y1 + Data.maxDepth * Math.sin(Math.PI - curr_angle));
                }

                curr_angle = Math.atan2(y2 - y1, x2 - x1);
                a *= 0.8;
                rayColor = multiply(rayColor, color, a);
            }
        }
    }

    private Color multiply(Color c1, Color c2, double a)
    {
        int r1 = c1.getRed();
        int g1 = c1.getGreen();
        int b1 = c1.getBlue();

        int r2 = c2.getRed();
        int g2 = c2.getGreen();
        int b2 = c2.getBlue();

        int r3 = (int) (r1 * r2 / 255 * a);
        int g3 = (int) (g1 * g2 / 255 * a);
        int b3 = (int) (b1 * b2 / 255 * a);

        return new Color(r3, g3, b3);
    }
}