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

    public enum Color {
        RED,
        GREEN,
        BLUE,
        GREY
    }

    public static Rgb fromColor(Color color)
    {
        switch ( color )
        {
            case RED:
                return new Rgb(0.7f, 0.2f, 0.2f);
            case GREEN:
                return new Rgb(0.2f, 0.7f, 0.2f);
            case BLUE:
                return new Rgb(0.2f, 0.2f, 0.7f);
            case GREY:
                return new Rgb(0.35f, 0.35f, 0.35f);
            default:
                return new Rgb(0.5f, 0.5f, 0.5f);
        }
    }

}
