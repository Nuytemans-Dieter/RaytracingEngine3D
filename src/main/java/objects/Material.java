package objects;

import graphics.Rgb;
import maths.vector.Point;
import objects.textures.ImageTexture;

public class Material {

    private final Rgb color;
    private Texture texture;

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

    private final double lightSpeed;

    public final float reflectivity;
    public final float transparency;
    public final float colorStrength;

    public Material(Rgb color, float diffusivity, float specularValue)
    {
        this(color, diffusivity, specularValue, 1);
    }

    public Material(Rgb color, float diffusivity, float specularValue, float roughness)
    {
        this(color, diffusivity, diffusivity, diffusivity, specularValue, specularValue, specularValue, roughness, 0.0f, 1, 0.0f);
    }

    public Material(Rgb color, float diffusivity, float specular, float roughness, float reflectivity, double lightSpeed, float transparency)
    {
        this(color, diffusivity, diffusivity, diffusivity, specular, specular, specular, roughness, reflectivity, lightSpeed, transparency);
    }

    public Material(Rgb color, float diffusivityR, float diffusivityG, float diffusivityB, float specularR, float specularG, float specularB, float roughness, float reflectivity, double lightSpeed, float transparency)
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

        this.lightSpeed = lightSpeed;

        this.reflectivity = reflectivity;
        this.transparency = transparency;
        this.colorStrength = Math.max( 1 - reflectivity - transparency, 0 );
    }

    public void setTexture(Texture newTexture)
    {
        this.texture = newTexture;
    }

    public boolean hasImageTexture()
    {
        return this.texture != null && this.texture instanceof ImageTexture;
    }

    public double getLightSpeed()
    {
        return lightSpeed;
    }

    public Rgb getColor(Point location)
    {
        return this.texture != null ? this.texture.getColor( location.getX(), location.getY(), location.getZ() ) : this.color.clone();
    }
}
