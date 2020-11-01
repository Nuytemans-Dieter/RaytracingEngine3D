package objects;

import graphics.Rgb;

public class Material {

    private final Rgb color;

    public final float ambientR;
    public final float ambientG;
    public final float ambientB;

    public final float diffusivityR;
    public final float diffusivityG;
    public final float diffusivityB;

    public final float specularR;
    public final float specularG;
    public final float specularB;
    public final int specularExponent;

    public Material(Rgb color, float diffusivity, float specularValue)
    {
        this(color, diffusivity, diffusivity, diffusivity, specularValue, specularValue, specularValue, 1);
    }

    public Material(Rgb color, float diffusivity, float specularValue, int specularExponent)
    {
        this(color, diffusivity, diffusivity, diffusivity, specularValue, specularValue, specularValue, specularExponent);
    }

    public Material(Rgb color, float diffusivityR, float diffusivityG, float diffusivityB, float specularR, float specularG, float specularB, int specularExponent)
    {
        this.color = color;

        this.diffusivityR = diffusivityR;
        this.diffusivityG = diffusivityG;
        this.diffusivityB = diffusivityB;

        this.specularR = specularR;
        this.specularG = specularG;
        this.specularB = specularB;

        this.ambientR = Math.max(1 - specularR - diffusivityR, 0);
        this.ambientG = Math.max(1 - specularG - diffusivityG, 0);
        this.ambientB = Math.max(1 - specularB - diffusivityB, 0);
        this.specularExponent = specularExponent;
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
