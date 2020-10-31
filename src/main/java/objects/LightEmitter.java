package objects;

import graphics.Rgb;
import maths.vector.Point;

public abstract class LightEmitter extends Positionable {

    protected double intensity;
    protected Rgb color;

    public LightEmitter(Point location, double intensity)
    {
        this(location, intensity, new Rgb(1.0f, 1.0f, 1.0f));
    }

    public LightEmitter(Point location, double intensity, Rgb color)
    {
        super(location);
        this.intensity = intensity;
        this.color = color.applyIntensity( (float) intensity );
    }

    public Rgb getColor()
    {
        return color;
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
