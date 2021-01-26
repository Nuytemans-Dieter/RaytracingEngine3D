package objects.materials;

import graphics.Rgb;
import objects.Material;
import objects.textures.ImageTexture;

public class Ice extends Material {

    public Ice()
    {
        super(
            new Rgb(0.7f, 0.7f, 0.7f),
            0.7f,
            0.6f,
            64,
            0.15f,
            0.5d,
            0f
        );
        setTexture( new ImageTexture( "ice.jpg" ));
    }

}
