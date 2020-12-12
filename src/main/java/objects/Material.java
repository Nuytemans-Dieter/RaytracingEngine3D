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
    public final float roughness;

    private final float[] lightSpeed;

    public final float reflectivity;
    public final float transparency;
    public final float colorStrength;

    public Material(Rgb color, float diffusivity, float specularValue)
    {
        this(color, diffusivity, specularValue, 1);
    }

    public Material(Rgb color, float diffusivity, float specularValue, float roughness)
    {
        this(color, diffusivity, diffusivity, diffusivity, specularValue, specularValue, specularValue, roughness, 0.0f, new float[]{50f, 50f, 50f}, 0.0f);
    }

    public Material(Rgb color, float diffusivity, float specular, float roughness, float reflectivity, float[] lightSpeed, float transparency)
    {
        this(color, diffusivity, diffusivity, diffusivity, specular, specular, specular, roughness, reflectivity, lightSpeed, transparency);
    }

    public Material(Rgb color, float diffusivityR, float diffusivityG, float diffusivityB, float specularR, float specularG, float specularB, float roughness, float reflectivity, float[] lightSpeed, float transparency)
    {
        this.color = color;

        this.diffusivityR = diffusivityR;
        this.diffusivityG = diffusivityG;
        this.diffusivityB = diffusivityB;

        this.specularR = specularR;
        this.specularG = specularG;
        this.specularB = specularB;
        this.roughness = roughness;

        this.ambientR = Math.max(1 - specularR - diffusivityR, 0);
        this.ambientG = Math.max(1 - specularG - diffusivityG, 0);
        this.ambientB = Math.max(1 - specularB - diffusivityB, 0);

        assert (lightSpeed.length == 3);
        this.lightSpeed = lightSpeed;

        this.reflectivity = reflectivity;
        this.transparency = transparency;
        this.colorStrength = Math.max( 1 - reflectivity - transparency, 0 );
    }

    public float[] getLightSpeed()
    {
        return new float[]
        {
            lightSpeed[0], lightSpeed[1], lightSpeed[2]
        };
    }

    public Rgb getColor()
    {
        return this.color.clone();
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
