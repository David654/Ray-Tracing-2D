package ray_tracing_2d_v2;

import java.awt.*;

public class Data
{
    public static final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = d.width / 2;
    public static int height = d.height / 2;
    public static int tile = width / 40;
    public static final int tickRate = 64;
    public static final String title = "Ray Tracing 2D V2";

    public static final double fov = Math.PI * 2;
    public static final int numRays = width;
    public static final double deltaAngle = fov / numRays;
    public static int maxDepth = width * 2;

    public static final int maxIter = 10;

    public static final Color bg = Color.black;
}