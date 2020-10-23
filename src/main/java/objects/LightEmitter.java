package objects;

import maths.vector.Point;

public abstract class LightEmitter extends Positionable {

    protected double intensity;

    public LightEmitter(Point location, double intensity)
    {
        super(location);
        this.intensity = intensity;
    }

    public double getIntensity()
    {
        return this.intensity;
    }

    /**
     * The threshold value of t
     */
    public abstract double getTValue();

}
