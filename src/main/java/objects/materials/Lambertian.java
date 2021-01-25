package objects.materials;


import graphics.Rgb;
import objects.Material;
import objects.textures.SineTexture;

public class Lambertian extends Material {

    public Lambertian(Rgb.Color color)
    {
        super
        (
            Rgb.fromColor( color ),
            0.5f,
            0.2f,
            2,
            0.0f,
            1,
            0.0f
        );
        setTexture( new SineTexture() );
    }

    public Lambertian()
    {
        this( Rgb.Color.GREY );
    }
}
