package objects.materials;


import graphics.Rgb;
import objects.Material;
import objects.Texture;
import objects.textures.CircleTexture;
import objects.textures.ImageTexture;

public class Lambertian extends Material {

    public Lambertian(Rgb color)
    {
        super
        (
            color,
            0.5f,
            0.2f,
            2,
            0.0f,
            1,
            0.0f
        );
    }

    public Lambertian(Rgb.Color color)
    {
        this( Rgb.fromColor( color ) );
    }

    public Lambertian(Texture texture)
    {
        this(Rgb.Color.WHITE);
        this.setTexture( texture );
    }

    public Lambertian()
    {
        this( Rgb.Color.GREY );
    }
}
