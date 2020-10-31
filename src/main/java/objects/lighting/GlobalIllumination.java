package objects.lighting;

import graphics.Rgb;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Ray;

public class GlobalIllumination extends LightEmitter {


    public GlobalIllumination(double illumination)
    {
        // Place at the origin, as location does not matter
        super(new Point(0, 0, 0), illumination, new Rgb(1, 1, 1));
    }

    @Override
    public double getTValue()
    {
        // Always return 0 so there can be no objects in the way
        return 0;
    }
}
