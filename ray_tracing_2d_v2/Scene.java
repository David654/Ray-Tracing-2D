package ray_tracing_2d_v2;

import ray_tracing_2d_v2.sceneobjects.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Scene extends Canvas implements Runnable
{
    private final Window window;
    private Thread thread;
    private boolean running = false;

    public Handler handler;
    public KeyInput keyInput;
    public MouseInput mouseInput;
    public Map map;

    public BufferedImage image;

    public Scene()
    {
        handler = new Handler();
        //handler.addSprite(new Player(Data.width / 2 - Data.tile / 2, Data.height / 2 - Data.tile / 2, Data.tile, Data.tile, ID.Player, Color.white, ID.LightSource, handler));
        handler.addSprite(new LightSource(Data.width - Data.tile, Data.height - Data.tile, Data.tile, Data.tile, ID.LightSource, Color.magenta, ID.LightSource));
        handler.addSprite(new LightSource(Data.width / 2 - Data.tile * 3, Data.tile * 4, Data.tile, Data.tile, ID.LightSource, Color.cyan, ID.LightSource));
        handler.addSprite(new LightSource(Data.width / 2 - Data.tile * 3, Data.height / 2 - Data.tile, Data.tile, Data.tile, ID.LightSource, Color.yellow, ID.LightSource));
        /*for(int x = 0; x < Data.width; x += 5)
        {
            for(int y = 0; y < Data.height; y += 5)
            {
                Random r = new Random();
                handler.addObject(new Ray(this, x, y, r.nextDouble(Math.PI * 2)));
            }
        }**/
        for(Sprite sprite : handler.sprites)
        {
            if(sprite.getMaterial() == ID.LightSource)
            {
                for(double i = -Data.fov / 2; i < Data.fov / 2; i += Data.deltaAngle)
                {
                    handler.addObject(new Ray(this, sprite.getX(), sprite.getY(), i, sprite));
                }
            }
        }
        map = new Map(handler);

        image = new BufferedImage(Data.width + 1, Data.height + 1, BufferedImage.TYPE_INT_RGB);

        keyInput = new KeyInput(handler);
        mouseInput = new MouseInput(handler);

        this.setSize(Data.width, Data.height);
        this.setPreferredSize(new Dimension(Data.width, Data.height));
        this.addKeyListener(keyInput);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        this.setFocusable(true);
        window = new Window(Data.width, Data.height, Data.title, this);
    }

    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = Data.tickRate;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1)
            {
                tick();
                delta--;
            }
            if(running)
            {
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                long end = System.nanoTime();
                float diff = (end - now) / 1000000f;
                window.setTitle(Data.title + " | FPS: " + frames + "; Render Latency: " + diff + " ms; " + "Tick: " + Data.tickRate);
                frames = 0;
            }
        }
        stop();
    }

    private void tick()
    {
        handler.tick();
        keyInput.tick();
    }

    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        image = createImage(Data.width + 1, Data.height + 1, BufferedImage.TYPE_INT_RGB, Color.black);

        Graphics2D ig = image.createGraphics();
        ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Data.bg);
        g2d.fillRect(0, 0, Data.width, Data.height);

        handler.render(ig);

        ig.dispose();

        //pixelate(Data.tile / 4);
        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();
        bs.show();
    }

    private BufferedImage createImage(int width, int height, int type, Color color)
    {
        BufferedImage image = new BufferedImage(width, height, type);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, width, height);
        return image;
    }

    private void pixelate(int size)
    {
        Raster src = image.getData();
        WritableRaster dest = src.createCompatibleWritableRaster();
        for(int y = 0; y < src.getHeight(); y += size)
        {
            for(int x = 0; x < src.getWidth(); x += size)
            {
                double[] pixel = new double[3];
                pixel = src.getPixel(x, y, pixel);

                for(int yd = y; (yd < y + size) && (yd < dest.getHeight()); yd++)
                {
                    for(int xd = x; (xd < x + size) && (xd < dest.getWidth()); xd++)
                    {
                        dest.setPixel(xd, yd, pixel);
                    }
                }
            }
        }
        image.setData(dest);
    }

    public static double clamp(double var, double min, double max)
    {
        if(var >= max) return var = max;
        else if(var <= min) return var = min;
        else return var;
    }
}