package graphics;

public class Rgb {

    private float r;
    private float g;
    private float b;

    /**
     * Red, green and blue components
     * Must all be in the range [0.0, 1.0]
     *
     * @param r red component
     * @param g green component
     * @param b blue component
     */
    public Rgb(float r, float g, float b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float r()
    {
        return r;
    }

    public float g()
    {
        return g;
    }

    public float b()
    {
        return b;
    }

    public Rgb addRgb(float r, float g, float b)
    {
        this.r += r;
        this.g += g;
        this.b += b;

        return this;
    }

    public Rgb addRgb(Rgb rgb)
    {
        return addRgb( rgb.r, rgb.g, rgb.b);
    }

    @Override
    public Rgb clone()
    {
        return new Rgb(r, g, b);
    }

    public Rgb applyIntensity(float intensity)
    {
        this.r = this.r * intensity;
        this.g = this.g * intensity;
        this.b = this.b * intensity;

        return this;
    }

    public Rgb applyIntensity(Rgb intensity)
    {
        this.r = this.r * intensity.r;
        this.g = this.g * intensity.g;
        this.b = this.b * intensity.b;

        return this;
    }

}
