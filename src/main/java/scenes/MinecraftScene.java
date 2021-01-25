package scenes;

import graphics.Rgb;
import interfaces.ITransMatFactory;
import interfaces.Scene;
import maths.TransMatFactory;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Object3D;
import objects.lighting.LightSource;
import objects.materials.Glass;
import objects.materials.Lambertian;
import objects.object3d.Cube;
import objects.object3d.Plane;
import objects.textures.ImageTexture;

import java.util.ArrayList;
import java.util.List;

public class MinecraftScene implements Scene {

    @Override
    public List<Object3D> getObjects() {

        final ITransMatFactory matrixFactory = new TransMatFactory();
        final List<Object3D> objects = new ArrayList<>();

        Object3D background = new Plane().setMaterial( new Lambertian( new Rgb((float) 0.5, (float) 0.92, 1) ) );
        background.addTransformations( matrixFactory.getScaling( 30, 20, 1 ) );
        background.addTransformations( matrixFactory.getTranslation( 0, 0, -20 ) );
        objects.add( background );

        Object3D sun = new Plane().setMaterial( new Lambertian( new Rgb((float) 0.8, (float) 0.8, 0) ).setEmissive(0.8f, 0.8f, 0) );
        sun.addTransformations( matrixFactory.getTranslation( 10, -10, -18 ) );
        objects.add( sun );

        // Build dirt ground
        for (int x = -7; x < 7; x++)
            for (int z = -6; z < 6; z++)
            {
                Object3D block = new Cube().setMaterial( new Lambertian( new ImageTexture( "dirt.jpg" ) ) );
                block.addTransformations( matrixFactory.getScaling(0.5, 0.5, 0.5) );
                block.addTransformations( matrixFactory.getTranslation(2*x, 4, 2*z) );
                objects.add( block );
            }

        // Build tree
        for (int i = 0; i < 7; i++)
        {
            Object3D wood = new Cube().setMaterial( new Lambertian( new ImageTexture( ("wood.jpg" ) ) ));
            wood.addTransformations( matrixFactory.getScaling(0.5, 0.5, 0.5) );
            wood.addTransformations( matrixFactory.getTranslation(-6, 2 - i, 0) );
            objects.add( wood );
        }


        // Build cobblestone wall
        for (int x = -1; x < 2; x++)
            for (int y = -1; y < 2; y++)
            {
                if (x != 0 || y != 0)
                {
                    Object3D cobble = new Cube().setMaterial( new Lambertian( new ImageTexture( ("cobblestone.png" ) ) ));
                    cobble.addTransformations( matrixFactory.getScaling(0.5, 0.5, 0.5) );
                    cobble.addTransformations( matrixFactory.getTranslation(2*x + 2, 2*y, 4) );
                    objects.add( cobble );
                }
            }


        // Build glass wall
        for (int x = -1; x < 1; x++)
            for (int y = -1; y < 1; y++)
            {
                Object3D cobble = new Cube().setMaterial( new Glass() );
                cobble.addTransformations( matrixFactory.getScaling(0.5, 0.5, 0.5) );
                cobble.addTransformations( matrixFactory.getTranslation(2*x - 4, 2*y + 2, 4) );
                objects.add( cobble );
            }

        return objects;
    }

    @Override
    public List<LightEmitter> getLights() {
        final List<LightEmitter> lights = new ArrayList<>();
        lights.add( new LightSource( new Point(10, -10, -17.99), 2.0, new Rgb(1,1,1) ) );
        lights.add( new LightSource( new Point(11, -11, -19), 3.0, new Rgb(1,1,1) ) );

        return lights;
    }
}
