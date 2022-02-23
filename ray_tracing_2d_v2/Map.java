package ray_tracing_2d_v2;

import ray_tracing_2d_v2.sceneobjects.Handler;
import ray_tracing_2d_v2.sceneobjects.ID;
import ray_tracing_2d_v2.sceneobjects.Wall;

import java.awt.*;

public class Map
{
    public String[] map = new String[]
            {
                    "................................",
                    "................................",
                    "..........3....3........44......",
                    "..........3....3................",
                    "..........3....3................",
                    "..........333333.........4......",
                    "................................",
                    "................................",
                    ".....................2...2......",
                    ".........444.........2...2......",
                    ".....................2...2......",
                    ".....................2...2......",
                    ".........................2......",
                    ".....4...............2...2......",
                    "...........4.........22222......",
                    "..........4.....................",
                    "..............1111..............",
                    "................................",
            };

    public Map(Handler handler)
    {
        int tile = Data.width / map[0].length();

        for(int i = 0; i < map.length; i++)
        {
            String row = map[i];
            for(int j = 0; j < row.length(); j++)
            {
                char ch = row.charAt(j);
                if(Character.isDigit(ch))
                {
                    Color color = new Color(255, 255, 255);
                    if(ch == '1')
                    {
                        color = new Color(147, 202, 237);
                    }
                    else if(ch == '2')
                    {
                        color = new Color(238, 238, 155);
                    }
                    else if(ch == '3')
                    {
                        color = new Color(244, 113, 116);
                    }
                    else if(ch == '4')
                    {
                        color = new Color(96, 177, 106);
                    }
                    color = new Color(150, 150, 150);
                    handler.sprites.add(new Wall(j * tile, i * tile, tile, tile, ID.Wall, color, ID.Metal));
                }
            }
        }
    }
}