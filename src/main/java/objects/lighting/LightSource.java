package objects.lighting;

import graphics.Rgb;
import maths.Vector;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Positionable;
import objects.Ray;

public class LightSource extends LightEmitter {

    public LightSource(Point location, double intensity)
    {
        super(location, intensity);
    }

    public LightSource(Point location, double intensity, Rgb color)
    {
        super(location, intensity, color);
    }

    @Override
    public double getTValue()
    {
        return 1;
    }
}
