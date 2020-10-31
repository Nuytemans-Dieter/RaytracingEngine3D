package objects;

import graphics.Rgb;

public class Material {

    private final Rgb color;

    public final float diffusivityR;
    public final float diffusivityG;
    public final float diffusivityB;

    public final float specularR;
    public final float specularG;
    public final float specularB;

    public Material(Rgb color, float diffusivity, float specularValue)
    {
        this(color, diffusivity, diffusivity, diffusivity, specularValue, specularValue, specularValue);
    }

    public Material(Rgb color, float diffusivityR, float diffusivityG, float diffusivityB, float specularR, float specularG, float specularB)
    {
        this.color = color;

        this.diffusivityR = diffusivityR;
        this.diffusivityG = diffusivityG;
        this.diffusivityB = diffusivityB;

        this.specularR = specularR;
        this.specularG = specularG;
        this.specularB = specularB;
    }

    public Rgb getColor()
    {
        return this.color;
    }

    public float r()
    {
        return this.color.r();
    }

    public float g()
    {
        return this.color.g();
    }

    public float b()
    {
        return this.color.b();
    }
}
